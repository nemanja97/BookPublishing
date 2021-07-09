package lu.ftn.paypalpaymentservice.service;

import com.paypal.api.payments.Currency;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import lu.ftn.paypalpaymentservice.model.*;
import lu.ftn.paypalpaymentservice.model.dto.InvoiceCreationDTO;
import lu.ftn.paypalpaymentservice.model.dto.PaymentStatusDTO;
import lu.ftn.paypalpaymentservice.model.dto.RedirectDTO;
import lu.ftn.paypalpaymentservice.model.dto.SubscriptionPlanDTO;
import lu.ftn.paypalpaymentservice.repository.PaymentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.*;

@Service
public class PaypalService {

    private final APIContext apiContext;
    private final ServletWebServerApplicationContext webServerAppCtxt;
    private final PaymentDataRepository paymentDataRepository;
    private final PaypalLogService paypalLogService;
    @Value("${paypal.mode}")
    private String mode;
    private final AccountService accountService;
    private final CustomBillingPlanService customBillingPlanService;
    private final CustomAgreementService customAgreementService;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    public PaypalService(APIContext apiContext, ServletWebServerApplicationContext webServerAppCtxt, PaymentDataRepository paymentDataRepository, PaypalLogService paypalLogService, AccountService accountService, CustomBillingPlanService customBillingPlanService, CustomAgreementService customAgreementService) {
        this.apiContext = apiContext;
        this.webServerAppCtxt = webServerAppCtxt;
        this.paymentDataRepository = paymentDataRepository;
        this.paypalLogService = paypalLogService;
        this.accountService = accountService;
        this.customBillingPlanService = customBillingPlanService;
        this.customAgreementService = customAgreementService;
    }

    private APIContext getContextForStore(String storeId) {
        AccountInfo accountInfo = accountService.get(storeId);
        APIContext context = new APIContext(accountInfo.getClientId(), accountInfo.getClientSecret(), "sandbox");
        return context;
    }

    public Payment createPayment(InvoiceCreationDTO dto) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(dto.getCurrency());
        amount.setTotal(String.format("%.2f", dto.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        int port = webServerAppCtxt.getWebServer().getPort();
        String succUrl = "https://localhost:" + port + "/api/paypal/success";
        String cancelUrl = "https://localhost:" + port + "/api/paypal/cancel";

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(succUrl);
        payment.setRedirectUrls(redirectUrls);

        APIContext context = getContextForStore(dto.getStoreId());
        context.setConfigurationMap(getPayPalSdkConfig());
        PaypalLog log = new PaypalLog(null, new Date(), "paypal", "", "created payment", "");
        paypalLogService.saveLog(log);

        payment = payment.create(context);

        AccountInfo accountInfo = accountService.get(dto.getStoreId());
        PaymentData paymentData = new PaymentData(payment.getId(), accountInfo.getStoreId(), dto.getId());
        save(paymentData);

        return payment;
    }

    public PaymentData findPaymentData(String paymentId) {
        return paymentDataRepository.findById(paymentId).orElseThrow(EntityNotFoundException::new);
    }

    public PaymentData save(PaymentData paymentData) {
        return paymentDataRepository.save(paymentData);
    }

    public Payment executePayment(String paymentId, String payerId, String sellerId, String sellerSecret, String storeId) throws PayPalRESTException {

        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(sellerId, sellerSecret, getPayPalSdkConfig());
        APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
        context.setConfigurationMap(getPayPalSdkConfig());
        System.out.println("Payment executing");
        PaypalLog log = new PaypalLog(null, new Date(), "paypal", storeId, "executing payment", "");
        paypalLogService.saveLog(log);
        return payment.execute(context, paymentExecution);
    }

    public Map<String, String> getPayPalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    public void saveSuccessfulPayment(String storeId) {
        PaypalLog log = new PaypalLog(null, new Date(), "paypal", storeId, "payment successful", "");
        paypalLogService.saveLog(log);
    }

    public void saveUnSuccessfulPayment(String errorMessage, String storeId) {
        PaypalLog log = new PaypalLog(null, new Date(), "paypal", storeId, "payment unsuccessful", errorMessage);
        paypalLogService.saveLog(log);
    }

    public void paymentCanceled() {
        PaypalLog log = new PaypalLog(null, new Date(), "paypal", "", "payment canceled", "");
        paypalLogService.saveLog(log);
    }

    public String createAgreement(Long customPlanId, String invoiceId) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

        CustomBillingPlan customBillingPlan = customBillingPlanService.findOne(customPlanId);

        Agreement agreement = new Agreement();
        agreement.setName("Subscription");
        agreement.setDescription("Paypal subscription");

        Instant i = java.time.Clock.systemUTC().instant();
        Instant j = i.plusSeconds(600);
        String startDateTime = (j.toString());
        String[] split = startDateTime.split("\\.");
        String startDate = split[0] + "Z";
        agreement.setStartDate(startDate);

        Plan plan = new Plan();
        plan.setId(customBillingPlan.getPlanId());
        agreement.setPlan(plan);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        agreement.setPayer(payer);

        APIContext context = getContextForStore(customBillingPlan.getStoreId());

        agreement = agreement.create(context);

        CustomAgreement customAgreement = new CustomAgreement();
        customAgreement.setBillingPlan(customBillingPlan);
        customAgreement.setToken(agreement.getToken());
        customAgreement.setStoreId(customBillingPlan.getStoreId());
        customAgreement.setStatus("created");
        customAgreement.setInvoiceId(invoiceId);
        customAgreement = customAgreementService.save(customAgreement);

        for (Links links : agreement.getLinks()) {
            if ("approval_url".equals(links.getRel())) {
                return links.getHref();
            }
        }
        return "";
    }

    public String createPlan(SubscriptionPlanDTO dto) throws PayPalRESTException {

        Plan plan = new Plan();
        plan.setType("fixed");
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());

        PaymentDefinition paymentDefinition = new PaymentDefinition();
        paymentDefinition.setName("Regular Payments");
        paymentDefinition.setType("REGULAR");
        paymentDefinition.setFrequency(dto.getFrequency());
        paymentDefinition.setFrequencyInterval(dto.getFreqInterval());
        paymentDefinition.setCycles(dto.getCycles());
        paymentDefinition.setAmount(new Currency(dto.getCurrency(), dto.getAmount()));

        String succUrl = "https://localhost:8080" + "/paypal-payment-service/api/paypal/successPlan";
        String cancelUrl = "https://localhost:8080"  + "/paypal-payment-service/api/paypal/cancelPlan";

        MerchantPreferences merchantPreferences = new MerchantPreferences();
        merchantPreferences.setReturnUrl(succUrl);
        merchantPreferences.setCancelUrl(cancelUrl);
        merchantPreferences.setAutoBillAmount("yes");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");
        merchantPreferences.setMaxFailAttempts("1");
        merchantPreferences.setSetupFee(new Currency(dto.getCurrency(), dto.getAmountStart()));
        ArrayList<PaymentDefinition> pd = new ArrayList<>();
        pd.add(paymentDefinition);
        plan.setPaymentDefinitions(pd);
        plan.setMerchantPreferences(merchantPreferences);


        APIContext context = getContextForStore(dto.getStoreId());

        Plan createdPlan = plan.create(context);

        List<Patch> patchRequestList = new ArrayList<>();
        Map<String, String> value = new HashMap<>();
        value.put("state", "ACTIVE");

        Patch patch = new Patch();
        patch.setOp("replace");
        patch.setValue(value);
        patch.setPath("/");
        patchRequestList.add(patch);

        createdPlan.update(apiContext, patchRequestList);

        CustomBillingPlan customBillingPlan = new CustomBillingPlan(createdPlan.getId(), createdPlan.getName(), dto.getStoreId(), "ACTIVE", createdPlan.getCreateTime(), createdPlan.getUpdateTime(), createdPlan.getDescription(), dto.getFrequency(), dto.getFreqInterval(), dto.getCycles(), Double.parseDouble(dto.getAmount()), dto.getCurrency(), Double.parseDouble(dto.getAmountStart()));
        customBillingPlanService.save(customBillingPlan);
        return "PlanCreated";
    }

    public RedirectView executePlan(String token) throws PayPalRESTException {
        Agreement agreement = new Agreement();
        agreement.setToken(token);
        CustomAgreement customAgreement = customAgreementService.findByToken(token);
        APIContext apiContext = getContextForStore(customAgreement.getStoreId());
        Agreement activeAgreement = agreement.execute(apiContext, agreement.getToken());
        System.out.println(activeAgreement.toJSON());
        customAgreement.setActiveAgreementId(activeAgreement.getId());
        if (activeAgreement.getState().equals("Active")) {
            customAgreement.setStatus("approved");
            customAgreement.setPayerEmail(activeAgreement.getPayer().getPayerInfo().getEmail());
            customAgreement.setPayerId(activeAgreement.getPayer().getPayerInfo().getPayerId());
            customAgreement.setStartDate(activeAgreement.getStartDate());
            customAgreement.setFinalPaymentDate(activeAgreement.getAgreementDetails().getFinalPaymentDate());
            customAgreementService.save(customAgreement);

            PaymentStatusDTO dto = new PaymentStatusDTO("success");
            ResponseEntity<RedirectDTO> redirectPath = restTemplate.postForEntity("http://gateway-service/kp-service/api/invoice/paypal/" + customAgreement.getInvoiceId() + "/success", dto,RedirectDTO.class);
            if (redirectPath.getStatusCode().is2xxSuccessful()) {
                RedirectView view = new RedirectView(redirectPath.getBody().getRedirectUrl());
                return view;
            }
        } else {
            PaymentStatusDTO dto = new PaymentStatusDTO("failure");
            ResponseEntity<RedirectDTO> redirectPath = restTemplate.postForEntity("http://gateway-service/kp-service/api/invoice/paypal/" + customAgreement.getInvoiceId() + "/failure", dto,RedirectDTO.class);
            if (redirectPath.getStatusCode().is2xxSuccessful()) {
                RedirectView view = new RedirectView(redirectPath.getBody().getRedirectUrl());
                return view;
            }
        }
        return null;
    }

    public RedirectView successfulPayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentData paymentData = findPaymentData(paymentId);
        AccountInfo accountInfo = accountService.get(paymentData.getStoreId());
        Payment payment = executePayment(paymentId, payerId, accountInfo.getClientId(), accountInfo.getClientSecret(), accountInfo.getStoreId());
        System.out.println(payment.toJSON());
        payment.getFailureReason();
        if (payment.getState().equals("approved")) {
            saveSuccessfulPayment(accountInfo.getStoreId());
            ResponseEntity<RedirectDTO> redirectPath = restTemplate.postForEntity(String.format("https://gateway-service/kp-service/api/invoice/paypal/%s/success", paymentData.getInvoiceId()), new PaymentStatusDTO("success"), RedirectDTO.class);
            if (redirectPath.getStatusCode().is2xxSuccessful()) {
                return new RedirectView(redirectPath.getBody().getRedirectUrl());
            }
            return new RedirectView("https://localhost:4201/success");
        } else if (payment.getState().equals("failed")) {
            saveUnSuccessfulPayment(payment.getFailureReason(), accountInfo.getStoreId());
            ResponseEntity<RedirectDTO> redirectPath = restTemplate.postForEntity(String.format("https://gateway-service/kp-service/api/invoice/paypal/%s/failure", paymentData.getInvoiceId()), new PaymentStatusDTO(payment.getFailureReason()), RedirectDTO.class);
            if (redirectPath.getStatusCode().is2xxSuccessful()) {
                return new RedirectView(redirectPath.getBody().getRedirectUrl());
            }
            return new RedirectView("https://localhost:4201/failed");
        }
        return null;
    }

    public List<CustomBillingPlan> getPlansForStore(String storeId) {
        List<CustomBillingPlan> plans = customBillingPlanService.findAllByStoreId(storeId);
        return plans;
    }
    public CustomBillingPlan getPlan(Long planId) {
        return customBillingPlanService.findOne(planId);
    }
}
