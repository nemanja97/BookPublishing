package lu.ftn.luaccountingservice.controller;

import lu.ftn.luaccountingservice.model.dto.*;
import lu.ftn.luaccountingservice.model.dto.BooksOrderDTO;
import lu.ftn.luaccountingservice.model.dto.InvoiceDTO;
import lu.ftn.luaccountingservice.model.dto.MembershipDTO;
import lu.ftn.luaccountingservice.model.dto.TransactionDTO;
import lu.ftn.luaccountingservice.model.entity.Invoice;
import lu.ftn.luaccountingservice.model.entity.User;
import lu.ftn.luaccountingservice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = "/api/invoice")
@CrossOrigin
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Bean("kpRestTemplate")
    public RestTemplate kpRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("kpRestTemplate")
    private RestTemplate kpRestTemplate;

    @Value("${api.kp}")
    private String kpApi;

    @Value("${api.frontend}")
    private String frontendApi;

    @Value("${kp.token}")
    private String kpStoreId;

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody BooksOrderDTO dto) {
        Invoice invoice = invoiceService.createInvoice(dto.getBookIds(), getCurrentUserId());
        ResponseEntity<String> kpUrl = kpRestTemplate.postForEntity(String.format("%s/invoice", kpApi), new InvoiceDTO(invoice, kpStoreId), String.class);

        if (kpUrl.getStatusCode().is2xxSuccessful()) {
            return kpUrl;
        } else {
            return new ResponseEntity<>(kpUrl.getStatusCode());
        }
    }

    @PostMapping(value = "subscription")
    public ResponseEntity<?> createSubscriptionInvoice(@RequestBody MembershipDTO dto) {
        Invoice invoice = invoiceService.createInvoiceForMembership(dto, getCurrentUserId());
        InvoiceDTO invoiceDTO = new InvoiceDTO(invoice, kpStoreId);
        invoiceDTO.setPlanId(dto.getPlanId());
        ResponseEntity<String> kpUrl = kpRestTemplate.postForEntity(String.format("%s/invoice/plan/", kpApi), invoiceDTO, String.class);
        if (kpUrl.getStatusCode().is2xxSuccessful()) {
            return kpUrl;
        } else {
            return new ResponseEntity<>(kpUrl.getStatusCode());
        }
    }

    @GetMapping(path = "success")
    public RedirectView redirectToSuccessPage() {
        return new RedirectView(String.format("%s/order/success/", frontendApi));
    }

    @PostMapping(value = "success")
    public ResponseEntity<HttpStatus> invoiceSuccessCallback(@RequestBody TransactionDTO dto) {
        try {
            invoiceService.markInvoicePaymentAsValid(dto.getId(), dto.getServiceIssuedId(), dto.getStoreIssuedInvoiceId(), dto.getType(), dto.getStatus(), dto.getUtcTransactionTime());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "failure")
    public RedirectView redirectToFailurePage() {
        return new RedirectView(String.format("%s/order/failure/", frontendApi));
    }


    @PostMapping(value = "failure")
    public ResponseEntity<HttpStatus> invoiceFailureCallback(@RequestBody TransactionDTO dto) {
        try {
            invoiceService.markInvoicePaymentAsInvalid(dto.getId(), dto.getServiceIssuedId(), dto.getStoreIssuedInvoiceId(), dto.getType(), dto.getStatus(), dto.getUtcTransactionTime());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    @GetMapping(value = "allPlans")
    public ResponseEntity<ListOfBillingPlansDTO> getMembershipForUser() {
        ResponseEntity<ListOfBillingPlansDTO> plans = kpRestTemplate.getForEntity(String.format("%s/invoice/plans/%s", kpApi,kpStoreId), ListOfBillingPlansDTO.class);
        return new ResponseEntity<>(plans.getBody(), HttpStatus.OK);
    }
}
