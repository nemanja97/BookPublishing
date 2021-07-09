package lu.ftn.kpservice.controller;

import lu.ftn.kpservice.model.dto.*;
import lu.ftn.kpservice.model.entity.Invoice;
import lu.ftn.kpservice.model.entity.InvoiceItem;
import lu.ftn.kpservice.model.entity.Store;
import lu.ftn.kpservice.service.InvoiceService;
import lu.ftn.kpservice.service.StoreService;
import lu.ftn.kpservice.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequestMapping(value = "api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    StoreService storeService;

    @Autowired
    TransactionsService transactionService;

    @LoadBalanced
    @Bean("invoiceRestTemplate")
    public RestTemplate invoiceRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("invoiceRestTemplate")
    private RestTemplate invoiceRestTemplate;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createInvoice(@Valid @RequestBody CreateInvoiceRequestDTO dto) {
        Invoice invoice = convertDTOtoInvoice(dto);
        invoice = invoiceService.save(invoice);
        return new ResponseEntity<>(String.format("https://localhost:4201/invoice/%s", invoice.getId()), HttpStatus.OK);
    }

    @PostMapping(path = "settle/{invoiceId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity settleInvoice(@Valid @RequestBody CreateInvoiceRequestDTO dto) {
        // TODO Implement invoice settlement
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @GetMapping(path = "/{invoiceId}", produces = "application/json")
    public ResponseEntity<InvoiceKPFrontendDTO> getInvoice(@PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);

        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new InvoiceKPFrontendDTO(invoice), HttpStatus.OK);
    }

    private Invoice convertDTOtoInvoice(CreateInvoiceRequestDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setAmount(dto.getAmount());
        invoice.setCurrency(dto.getCurrency());
        invoice.setStoreIssuedId(dto.getId());

        Store store = storeService.getById(dto.getStoreId());
        invoice.setSeller(store);

        for (InvoiceItemDTO itemDTO : dto.getItems()) {
            InvoiceItem item = new InvoiceItem();
            item.setName(itemDTO.getName());
            item.setAmount(itemDTO.getAmount());
            item.setCurrency(itemDTO.getCurrency());
            item.setStoreIssuedId(itemDTO.getId());

            Store itemStore = storeService.getById(itemDTO.getStoreId());
            item.setSeller(itemStore);
            invoice.getItems().add(item);
        }
        return invoice;
    }

    private Invoice convertPlanDTOtoInvoice(CreateInvoiceRequestForPlanDTO dto) {
        Invoice invoice = new Invoice();
        ResponseEntity<PaypalSubscriptionPlanDTO> plan =
                invoiceRestTemplate.getForEntity("https://gateway-service/paypal-payment-service/api/paypal/plan/"+dto.getPlanId(),PaypalSubscriptionPlanDTO.class);
        invoice.setAmount(new BigDecimal(plan.getBody().getAmount()));
        invoice.setCurrency(plan.getBody().getCurrency());
        invoice.setStoreIssuedId(dto.getId());
        invoice.setPlanId(dto.getPlanId());
        Store store = storeService.getById(dto.getStoreId());
        invoice.setSeller(store);

        return invoice;
    }

    @PostMapping(value="plan",consumes = "application/json", produces = "application/json")
    public ResponseEntity createInvoiceForPlan(@Valid @RequestBody CreateInvoiceRequestForPlanDTO dto) {
        Invoice invoice = convertPlanDTOtoInvoice(dto);
        invoice = invoiceService.save(invoice);
        return new ResponseEntity<>(String.format("https://localhost:4201/invoicePlan/%s", invoice.getId()), HttpStatus.OK);
    }
}
