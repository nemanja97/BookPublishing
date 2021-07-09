package lu.ftn.kpservice.controller;

import com.google.common.collect.Lists;
import lu.ftn.kpservice.helper.Converter;
import lu.ftn.kpservice.model.dto.*;
import lu.ftn.kpservice.model.entity.*;
import lu.ftn.kpservice.model.enums.TransactionType;
import lu.ftn.kpservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping(value = "api/invoice")
public class PayPalInvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    StoreService storeService;

    @Autowired
    TransactionsService transactionService;

    @Autowired
    LogsService logsService;

    @Autowired
    UserService userService;

    @LoadBalanced
    @Bean("paypalInvoicePaymentRestTemplate")
    public RestTemplate paypalInvoicePaymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean("customerPaypalWebhookRestTemplate")
    public RestTemplate customerPaypalWebhookRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("customerPaypalWebhookRestTemplate")
    private RestTemplate customerPaypalWebhookRestTemplate;

    @Autowired
    @Qualifier("paypalInvoicePaymentRestTemplate")
    private RestTemplate paypalInvoicePaymentRestTemplate;

    @PostMapping(path = "/payment/paypal/{invoiceId}", produces = "application/json")
    public ResponseEntity payInvoiceViaPaypal(@PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (invoice.getCustomerTransaction() != null && Lists.newArrayList("success", "paid", "complete").contains(invoice.getCustomerTransaction().getStatus()))
            return new ResponseEntity<>("Invoice already paid", HttpStatus.BAD_REQUEST);

        InvoicePaymentDTO paymentDTO = Converter.invoiceToInvoicePaymentDTO(
                invoice,
                invoice.getSeller().getTransactionSuccessWebhook(),
                invoice.getSeller().getTransactionFailureWebhook()
        );

        ResponseEntity<InvoiceResponseDTO> responseDTO = paypalInvoicePaymentRestTemplate.exchange(
                "https://gateway-service/paypal-payment-service/api/paypal/pay/", HttpMethod.POST,
                new HttpEntity<>(paymentDTO), InvoiceResponseDTO.class);
        InvoiceResponseDTO response = responseDTO.getBody();

        boolean settled = invoice.getItems().stream().allMatch(item -> item.getSeller().getId().equals(invoice.getSeller().getId()));
        Transaction transaction = new Transaction("",
                response.getId(), invoice, TransactionType.PAYPAL, response.getStatus(),
                response.getTransferredCurrencyAmount(), response.getUrl(),
                new Date(response.getUtcTransactionTime()), invoice.getSeller(), settled
        );

        logTransaction("PayPalInvoiceController.payInvoiceViaPaypal",
                invoiceId, invoice.getSeller().getId(), "created", response.getItemDescription());

        transaction = transactionService.save(transaction);

        notifyClientAboutTransactionUpdate(invoice, transaction, true);

        return new ResponseEntity<>(new PayPalInvoiceDTO(response.getUrl(), invoiceId), HttpStatus.OK);
    }

    @GetMapping(path = "paypal/{invoiceId}", produces = "application/json")
    public ResponseEntity checkInvoiceOutcome(@PathVariable String invoiceId) {
        // TODO Redo if Paypal webhook is enabled
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ResponseEntity<InvoiceResponseDTO> response = getInvoiceFromPayPalService(invoice);
        if (responseWithoutError(response))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        InvoiceResponseDTO responseDTO = response.getBody();

        Transaction transaction = updateTransaction(invoice, responseDTO);
        logTransaction("PayPalInvoiceController.checkInvoiceOutcome",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), responseDTO.getItemDescription());
        notifyClientAboutTransactionUpdate(invoice, transaction, responseDTO.getExceptionStatus().equals("false"));

        return new ResponseEntity<>(responseDTO.getStatus(), HttpStatus.OK);
    }

    private boolean responseWithoutError(ResponseEntity<InvoiceResponseDTO> response) {
        return !response.getStatusCode().is2xxSuccessful() || response.getBody() == null;
    }

    private Transaction updateTransaction(Invoice invoice, InvoiceResponseDTO responseDTO) {
        Transaction transaction = invoice.getCustomerTransaction();
        if (responseDTO.getExceptionStatus().equals("false"))
            transaction.setStatus(responseDTO.getStatus());
        else
            transaction.setStatus(responseDTO.getExceptionStatus());
        transactionService.save(transaction);
        return transaction;
    }

    private Transaction updateTransaction(Invoice invoice, String status) {
        Transaction transaction = invoice.getCustomerTransaction();
        transaction.setStatus(status);
        transactionService.save(transaction);
        return transaction;
    }

    @PostMapping(path = "paypal/{invoiceId}/success", produces = "application/json")
    public ResponseEntity setPaypalInvoiceSuccess(@Valid @RequestBody PayPalInvoiceStatusDTO dto, @PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Transaction transaction = updateTransaction(invoice, dto.getStatus());
        logTransaction("PayPalInvoiceController.checkInvoiceOutcome",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), "");
        notifyClientAboutTransactionUpdate(invoice, transaction, true);

        return new ResponseEntity<>(new RedirectDTO(invoice.getSeller().getTransactionSuccessWebhook()), HttpStatus.OK);
    }

    @PostMapping(path = "paypal/{invoiceId}/failure", produces = "application/json")
    public ResponseEntity setPaypalInvoiceFailure(@Valid @RequestBody PayPalInvoiceStatusDTO dto, @PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Transaction transaction = updateTransaction(invoice, dto.getStatus());
        logTransaction("PayPalInvoiceController.checkInvoiceOutcome",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), "");
        notifyClientAboutTransactionUpdate(invoice, transaction, false);

        return new ResponseEntity<>(new RedirectDTO(invoice.getSeller().getTransactionFailureWebhook()), HttpStatus.OK);
    }

    private void logTransaction(String service, String invoiceId, String merchantId, String status, String description) {
        logsService.savePaypalLog(new PaypalLog(service, invoiceId, merchantId, status, description));
    }

    private void notifyClientAboutTransactionUpdate(Invoice invoice, Transaction transaction, boolean isPositiveOutcome) {
        notifyClientStoreAboutTransactionUpdate(invoice, transaction, isPositiveOutcome);
        notifyClientMerchantsAboutTransactionUpdate(invoice, transaction, isPositiveOutcome);
    }

    private void notifyClientStoreAboutTransactionUpdate(Invoice invoice, Transaction transaction, boolean isPositiveOutcome) {
        String url = isPositiveOutcome ? invoice.getSeller().getTransactionSuccessWebhook() :
                invoice.getSeller().getTransactionFailureWebhook();
        StoreTransactionDTO transactionDTO = new StoreTransactionDTO(invoice, transaction, TransactionType.PAYPAL);
        try{
            postForCustomerWebhook("PayPalInvoiceController.notifyClientStoreAboutTransactionUpdate", invoice, url, transactionDTO);
        } catch (Exception e){
            logsService.savePaypalLog(new PaypalLog("PaypalInvoiceController", invoice.getId(), invoice.getSeller().getId(), "Failure", "Unable to contact webhook"));
        }
    }

    private void notifyClientMerchantsAboutTransactionUpdate(Invoice invoice, Transaction transaction, boolean isPositiveOutcome) {
        Set<InvoiceItem> invoiceItems = invoice.getItems();

        for (InvoiceItem item : invoiceItems) {
            if (!item.getSeller().getId().equals(invoice.getSeller().getId())) {
                // TODO Parallelize
                String url = isPositiveOutcome ? item.getSeller().getTransactionSuccessWebhook() :
                        item.getSeller().getTransactionFailureWebhook();
                StoreTransactionDTO transactionDTO = new StoreTransactionDTO(item, transaction, TransactionType.PAYPAL);
                postForCustomerWebhook("PayPalInvoiceController.notifyClientMerchantsAboutTransactionUpdate", invoice, url, transactionDTO);
            }
        }
    }

    private void postForCustomerWebhook(String service, Invoice invoice, String url, StoreTransactionDTO transactionDTO) {
        try {
            customerPaypalWebhookRestTemplate.postForEntity(url, new HttpEntity<>(transactionDTO), Void.class);
        } catch (HttpClientErrorException ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook client error", String.format("Sending data to %s led to: %s", url, ex.getMessage()));
        } catch (HttpServerErrorException ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook server error", String.format("Sending data to %s led to: %s", url, ex.getMessage()));
        }
    }

    private ResponseEntity<InvoiceResponseDTO> getInvoiceFromPayPalService(Invoice invoice) {
        return paypalInvoicePaymentRestTemplate.getForEntity(
                String.format("https://gateway-service/paypal-payment-service/api/payment/%s/%s",
                        invoice.getSeller().getId(), invoice.getCustomerTransaction().getServiceIssuedId()),
                InvoiceResponseDTO.class);
    }


    @PostMapping(value = "/createPlan")
    public ResponseEntity<String> createPlan(@RequestBody PaypalSubscriptionPlanDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.getUserById(name);
        Store store = user.getManagedStore();
        dto.setStoreId(store.getId());
        dto.setId(null);
        ResponseEntity<String> response = paypalInvoicePaymentRestTemplate.postForEntity("https://gateway-service/paypal-payment-service/api/paypal/plan", dto, String.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/createAgreement/{invoiceId}")
    public ResponseEntity<String> createAgreement(@PathVariable("invoiceId") String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        ResponseEntity<InvoiceResponseDTO> responseDTO = paypalInvoicePaymentRestTemplate.getForEntity("https://gateway-service/paypal-payment-service/api/paypal/agreement/" + invoice.getPlanId() + "/" + invoice.getId(), InvoiceResponseDTO.class);
        InvoiceResponseDTO response = responseDTO.getBody();
        boolean settled = invoice.getItems().stream().allMatch(item -> item.getSeller().getId().equals(invoice.getSeller().getId()));
        Transaction transaction = new Transaction("",
                response.getId(), invoice, TransactionType.PAYPAL, response.getStatus(),
                response.getTransferredCurrencyAmount(), response.getUrl(),
                new Date(response.getUtcTransactionTime()), invoice.getSeller(), settled
        );
        transaction = transactionService.save(transaction);
        try{
            notifyClientAboutTransactionUpdate(invoice, transaction, true);
        } catch (Exception e){
            logsService.savePaypalLog(new PaypalLog("PaypalInvoiceController", invoice.getId(), invoice.getSeller().getId(), "Failure", "Unable to contact webhook"));
        }

        return new ResponseEntity<String>(response.getUrl(), HttpStatus.OK);
    }

    @GetMapping(value = "plans/{storeId}")
    public ResponseEntity<ListOfBillingPlans> getPlans(@PathVariable("storeId") String storeId) {
        ResponseEntity<ListOfBillingPlans> response = paypalInvoicePaymentRestTemplate.getForEntity("https://gateway-service/paypal-payment-service/api/paypal/plans/" + storeId, ListOfBillingPlans.class);
        return response;
    }

    @GetMapping(value = "plans")
    public ResponseEntity<ListOfBillingPlans> getPlansForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.getUserById(name);
        Store store = user.getManagedStore();
        String storeId = store.getId();
        ResponseEntity<ListOfBillingPlans> response = paypalInvoicePaymentRestTemplate.getForEntity("https://gateway-service/paypal-payment-service/api/paypal/plans/" + storeId, ListOfBillingPlans.class);
        return response;
    }

    @GetMapping(value = "plan/{invoiceId}")
    public ResponseEntity<PaypalSubscriptionPlanDTO> getPlan(@PathVariable("invoiceId") String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        ResponseEntity<PaypalSubscriptionPlanDTO> response = paypalInvoicePaymentRestTemplate.getForEntity("https://gateway-service/paypal-payment-service/api/paypal/plan/" + invoice.getPlanId(), PaypalSubscriptionPlanDTO.class);
        return response;
    }
}
