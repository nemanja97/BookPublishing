package lu.ftn.paypalpaymentservice.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lu.ftn.paypalpaymentservice.helper.Converter;
import lu.ftn.paypalpaymentservice.model.CustomBillingPlan;
import lu.ftn.paypalpaymentservice.model.dto.InvoiceCreationDTO;
import lu.ftn.paypalpaymentservice.model.dto.InvoiceResponseDTO;
import lu.ftn.paypalpaymentservice.model.dto.ListOfPlans;
import lu.ftn.paypalpaymentservice.model.dto.SubscriptionPlanDTO;
import lu.ftn.paypalpaymentservice.service.PaypalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/paypal")
@CrossOrigin
public class PaymentController {
    private final PaypalService paypalService;

    public PaymentController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping(value = "pay")
    public ResponseEntity<InvoiceResponseDTO> pay(@RequestBody InvoiceCreationDTO dto) throws PayPalRESTException {
        Payment payment = paypalService.createPayment(dto);
        InvoiceResponseDTO responseDTO = new InvoiceResponseDTO();
        responseDTO.setCurrency(dto.getCurrency());
        responseDTO.setId(dto.getId());
        responseDTO.setUtcTransactionTime((new Date()).getTime());
        responseDTO.setTransferredCurrencyAmount(dto.getAmount().doubleValue());
        responseDTO.setStatus(payment.getState());
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                responseDTO.setUrl(link.getHref());
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping(value = "cancel")
    public String cancel(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        paypalService.paymentCanceled();
        return "cancel";
    }

    @GetMapping(value = "success")
    public RedirectView success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            return paypalService.successfulPayment(paymentId, payerId);
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("https://localhost:4201");
    }


    @GetMapping(value = "cancelPlan")
    public String cancelPlan(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        paypalService.paymentCanceled();
        return "cancel";
    }

    @GetMapping(value = "successPlan")
    public RedirectView successPlan(@RequestParam("token") String token, @RequestParam("ba_token") String ba_token) {
        try {
            RedirectView redirectView = paypalService.executePlan(token);
            return redirectView;
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("https://localhost:4201");
    }

    @PostMapping(value = "/plan")
    private ResponseEntity<String> createPlan(@RequestBody SubscriptionPlanDTO dto) throws PayPalRESTException {

        return new ResponseEntity<>(paypalService.createPlan(dto), HttpStatus.OK);
    }

    @GetMapping(value = "/plan/{planId}")
    private ResponseEntity<SubscriptionPlanDTO> getPlan(@PathVariable("planId") Long planId) throws PayPalRESTException {
        CustomBillingPlan customBillingPlan = paypalService.getPlan(planId);
        SubscriptionPlanDTO subscriptionPlanDTO = Converter.createSubscriptionPlanDTO(customBillingPlan);
        return new ResponseEntity<>(subscriptionPlanDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/plans/{storeId}")
    private ResponseEntity<ListOfPlans> getPlans(@PathVariable("storeId") String storeId) throws PayPalRESTException {
        List<CustomBillingPlan> plans = paypalService.getPlansForStore(storeId);
        List<SubscriptionPlanDTO> dtos = plans.stream().map(Converter::createSubscriptionPlanDTO).collect(Collectors.toList());

        ListOfPlans plansList = new ListOfPlans(dtos);

        return new ResponseEntity<>(plansList, HttpStatus.OK);
    }

    @GetMapping(value = "/agreement/{planId}/{invoiceId}")
    private ResponseEntity<InvoiceResponseDTO> createAgreement(@PathVariable("planId") Long planId, @PathVariable("invoiceId") String invoiceId) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
        String url = paypalService.createAgreement(planId, invoiceId);
        CustomBillingPlan plan = paypalService.getPlan(planId);
        InvoiceResponseDTO responseDTO = new InvoiceResponseDTO();
        responseDTO.setCurrency(plan.getCurrency());
        responseDTO.setId(invoiceId);
        responseDTO.setUtcTransactionTime((new Date()).getTime());
        responseDTO.setTransferredCurrencyAmount(plan.getAmount());
        responseDTO.setStatus("created");
        responseDTO.setUrl(url);
        return new ResponseEntity<InvoiceResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
