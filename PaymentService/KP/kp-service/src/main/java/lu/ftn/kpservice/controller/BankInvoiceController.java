package lu.ftn.kpservice.controller;

import com.google.common.collect.Lists;
import lu.ftn.kpservice.helper.Converter;
import lu.ftn.kpservice.model.dto.*;
import lu.ftn.kpservice.model.entity.*;
import lu.ftn.kpservice.model.enums.TransactionType;
import lu.ftn.kpservice.service.InvoiceService;
import lu.ftn.kpservice.service.LogsService;
import lu.ftn.kpservice.service.StoreService;
import lu.ftn.kpservice.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/invoice")
public class BankInvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    StoreService storeService;

    @Autowired
    TransactionsService transactionService;

    @Autowired
    LogsService logsService;

    @LoadBalanced
    @Bean("bankInvoicePaymentRestTemplate")
    public RestTemplate bankInvoicePaymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("bankInvoicePaymentRestTemplate")
    private RestTemplate bankInvoicePaymentRestTemplate;

    @Bean("customerBankWebhookRestTemplate")
    public RestTemplate customerBankWebhookRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("customerBankWebhookRestTemplate")
    private RestTemplate customerBankWebhookRestTemplate;

    @PostMapping(path = "/payment/bank/{invoiceId}", produces = "application/json")
    public ResponseEntity payInvoiceViaBank(@PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (invoice.getCustomerTransaction() != null && Lists.newArrayList("success", "paid", "complete").contains(invoice.getCustomerTransaction().getStatus()))
            return new ResponseEntity<>("Invoice already paid", HttpStatus.BAD_REQUEST);

        ResponseEntity<BankInvoiceDTO> response = handlePaymentViaService(invoice);
        if (responseWithError(response))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        BankInvoiceDTO responseDTO = response.getBody();

        Transaction transaction = recordInitialTransaction(invoice, responseDTO);
        logTransaction("BankInvoiceController.payInvoiceViaBank",
                invoiceId, invoice.getSeller().getId(), "created", invoice.getItems().stream().map(InvoiceItem::getName).collect(Collectors.joining(",")));
        notifyClientAboutTransactionUpdate(invoice, transaction, response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }


    @PostMapping(path = "bank/{invoiceId}/success", produces = "application/json")
    public ResponseEntity setBankInvoiceSuccess(@RequestBody BankPaymentResponseDTO dto, @PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Transaction transaction = updateTransaction(invoice, dto);
        logTransaction("BankInvoiceController.setBankInvoiceSuccess",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), invoice.getItems().stream().map(InvoiceItem::getName).collect(Collectors.joining(",")));
        notifyClientAboutTransactionUpdate(invoice, transaction, HttpStatus.OK);

        return new ResponseEntity<>(new RedirectDTO(invoice.getSeller().getTransactionSuccessWebhook()), HttpStatus.OK);
    }

    @PostMapping(path = "bank/{invoiceId}/failure", produces = "application/json")
    public ResponseEntity setBankInvoiceFailure(@RequestBody BankPaymentResponseDTO dto, @PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Transaction transaction = updateTransaction(invoice, dto);
        logTransaction("BankInvoiceController.setBankInvoiceFailure",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), invoice.getItems().stream().map(InvoiceItem::getName).collect(Collectors.joining(",")));
        notifyClientAboutTransactionUpdate(invoice, transaction, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new RedirectDTO(invoice.getSeller().getTransactionFailureWebhook()), HttpStatus.OK);
    }

    @PostMapping(path = "bank/{invoiceId}/error", produces = "application/json")
    public ResponseEntity setBankInvoiceError(@RequestBody BankPaymentResponseDTO dto, @PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Transaction transaction = updateTransaction(invoice, dto);
        logTransaction("BankInvoiceController.setBankInvoiceError",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), invoice.getItems().stream().map(InvoiceItem::getName).collect(Collectors.joining(",")));
        notifyClientAboutTransactionUpdate(invoice, transaction, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(new RedirectDTO(invoice.getSeller().getTransactionErrorWebhook()), HttpStatus.OK);
    }


    private ResponseEntity<BankInvoiceDTO> handlePaymentViaService(Invoice invoice) {
        BankInvoiceCreationDTO paymentDTO = Converter.invoiceToBankInvoiceCreationDTO(
                invoice,
                String.format("https://localhost:8080/kp-service/api/invoice/bank/%s/success", invoice.getId()),
                String.format("https://localhost:8080/kp-service/api/invoice/bank/%s/failure", invoice.getId()),
                String.format("https://localhost:8080/kp-service/api/invoice/bank/%s/error", invoice.getId())
        );

        ResponseEntity<BankInvoiceDTO> response = bankInvoicePaymentRestTemplate.exchange(
        "https://gateway-service/bank-payment-service/api/payment/", HttpMethod.POST,
        new HttpEntity<>(paymentDTO), BankInvoiceDTO.class);
        return response;
    }

    private boolean responseWithError(ResponseEntity<BankInvoiceDTO> response) {
        return !response.getStatusCode().is2xxSuccessful() || response.getBody() == null;
    }

    private Transaction recordInitialTransaction(Invoice invoice, BankInvoiceDTO responseDTO) {
        boolean settled = invoice.getItems().stream().allMatch(item -> item.getSeller().getId().equals(invoice.getSeller().getId()));
        Transaction transaction = new Transaction("",
                responseDTO.getPaymentId(), invoice, TransactionType.CARD, "created",
                invoice.getAmount(), responseDTO.getPaymentUrl(),
                new Date(), invoice.getSeller(), settled
        );
        return transactionService.save(transaction);
    }

    private void logTransaction(String service, String invoiceId, String merchantId, String status, String description) {
        logsService.saveBankLog(new BankLog(service, invoiceId, merchantId, status, description));
    }

    private void notifyClientAboutTransactionUpdate(Invoice invoice, Transaction transaction, HttpStatus status) {
        notifyClientStoreAboutTransactionUpdate(invoice, transaction, status);
        notifyClientMerchantsAboutTransactionUpdate(invoice, transaction, status);
    }

    private void notifyClientStoreAboutTransactionUpdate(Invoice invoice, Transaction transaction, HttpStatus status) {
        String url;
        if (status.is2xxSuccessful()) {
            url = invoice.getSeller().getTransactionSuccessWebhook();
        } else if (status.is4xxClientError()) {
            url = invoice.getSeller().getTransactionFailureWebhook();
        } else if (status.is5xxServerError()) {
            url = invoice.getSeller().getTransactionErrorWebhook();
        } else {
            url = invoice.getSeller().getTransactionErrorWebhook();
        }

        StoreTransactionDTO transactionDTO = new StoreTransactionDTO(invoice, transaction, TransactionType.CARD);
        postForCustomerWebhook("BankInvoiceController.notifyClientStoreAboutTransactionUpdate", invoice, url, transactionDTO);
    }

    private void notifyClientMerchantsAboutTransactionUpdate(Invoice invoice, Transaction transaction, HttpStatus status) {
        Set<InvoiceItem> invoiceItems = invoice.getItems();

        for (InvoiceItem item : invoiceItems) {
            if (!item.getSeller().getId().equals(invoice.getSeller().getId())) {
                // TODO Parallelize
                String url;
                if (status.is2xxSuccessful()) {
                    url = item.getSeller().getTransactionSuccessWebhook();
                } else if (status.is4xxClientError()) {
                    url = item.getSeller().getTransactionFailureWebhook();
                } else if (status.is5xxServerError()) {
                    url = item.getSeller().getTransactionErrorWebhook();
                } else {
                    url = item.getSeller().getTransactionErrorWebhook();
                }

                StoreTransactionDTO transactionDTO = new StoreTransactionDTO(item, transaction, TransactionType.CARD);
                postForCustomerWebhook("BankInvoiceController.notifyClientMerchantsAboutTransactionUpdate", invoice, url, transactionDTO);
            }
        }
    }

    private void postForCustomerWebhook(String service, Invoice invoice, String url, StoreTransactionDTO transactionDTO) {
        try {
            customerBankWebhookRestTemplate.postForEntity(url, new HttpEntity<>(transactionDTO), Void.class);
        } catch (HttpClientErrorException ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook client error", String.format("Sending data to %s led to: %s", url, ex.getMessage()));
        } catch (HttpServerErrorException ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook server error", String.format("Sending data to %s led to: %s", url, ex.getMessage()));
        } catch (Exception ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook server error", String.format("Could not send data to: %s", url));
        }
    }

    private Transaction updateTransaction(Invoice invoice, BankPaymentResponseDTO responseDTO) {
        Transaction transaction = invoice.getCustomerTransaction();
        transaction.setStatus(responseDTO.getTransactionStatus());
        transactionService.save(transaction);
        return transaction;
    }
}
