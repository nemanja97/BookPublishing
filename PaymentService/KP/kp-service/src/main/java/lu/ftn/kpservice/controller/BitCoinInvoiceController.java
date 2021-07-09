package lu.ftn.kpservice.controller;

import com.google.common.collect.Lists;
import lu.ftn.kpservice.helper.Converter;
import lu.ftn.kpservice.model.dto.InvoicePaymentDTO;
import lu.ftn.kpservice.model.dto.InvoiceResponseDTO;
import lu.ftn.kpservice.model.dto.StoreTransactionDTO;
import lu.ftn.kpservice.model.entity.BitcoinLog;
import lu.ftn.kpservice.model.entity.Invoice;
import lu.ftn.kpservice.model.entity.InvoiceItem;
import lu.ftn.kpservice.model.entity.Transaction;
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
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping(value = "api/invoice")
public class BitCoinInvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    StoreService storeService;

    @Autowired
    TransactionsService transactionService;

    @Autowired
    LogsService logsService;

    @LoadBalanced
    @Bean("bitcoinInvoicePaymentRestTemplate")
    public RestTemplate bitcoinInvoicePaymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("bitcoinInvoicePaymentRestTemplate")
    private RestTemplate bitcoinInvoicePaymentRestTemplate;

    @Bean("customerWebhookRestTemplate")
    public RestTemplate customerWebhookRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("customerWebhookRestTemplate")
    private RestTemplate customerWebhookRestTemplate;

    @PostMapping(path = "/payment/bitcoin/{invoiceId}", produces = "application/json")
    public ResponseEntity payInvoiceViaBitcoin(@PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (invoice.getCustomerTransaction() != null && Lists.newArrayList("success", "paid", "complete").contains(invoice.getCustomerTransaction().getStatus()))
            return new ResponseEntity<>("Invoice already paid", HttpStatus.BAD_REQUEST);

        ResponseEntity<InvoiceResponseDTO> response = handlePaymentViaService(invoice);
        if (responseWithError(response))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        InvoiceResponseDTO responseDTO = response.getBody();

        Transaction transaction = recordInitialTransaction(invoice, responseDTO);
        logTransaction("BitCoinInvoiceController.payInvoiceViaBitcoin",
                invoiceId, invoice.getSeller().getId(), "created", responseDTO.getItemDescription());
        notifyClientAboutTransactionUpdate(invoice, transaction, true);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(path = "bitcoin/{invoiceId}", produces = "application/json")
    public RedirectView checkInvoiceOutcome(@PathVariable String invoiceId) {
        // TODO Redo if Bitcoin webhook is enabled
        Invoice invoice = invoiceService.getById(invoiceId);
        if (invoice == null)
            return new RedirectView(invoice.getSeller().getTransactionFailureWebhook());

        ResponseEntity<InvoiceResponseDTO> response = getInvoiceFromBitcoinService(invoice);
        if (responseWithError(response))
            return new RedirectView(invoice.getSeller().getTransactionFailureWebhook());
        InvoiceResponseDTO responseDTO = response.getBody();

        Transaction transaction = updateTransaction(invoice, responseDTO);
        logTransaction("BitCoinInvoiceController.checkInvoiceOutcome",
                invoiceId, invoice.getSeller().getId(), transaction.getStatus(), responseDTO.getItemDescription());
        notifyClientAboutTransactionUpdate(invoice, transaction, responseDTO.getExceptionStatus().equals("false"));

        return new RedirectView(invoice.getSeller().getTransactionSuccessWebhook());
    }

    private boolean responseWithError(ResponseEntity<InvoiceResponseDTO> response) {
        return !response.getStatusCode().is2xxSuccessful() || response.getBody() == null;
    }

    private void logTransaction(String service, String invoiceId, String merchantId, String status, String description) {
        logsService.saveBitcoinLog(new BitcoinLog(service, invoiceId, merchantId, status, description));
    }

    private Transaction recordInitialTransaction(Invoice invoice, InvoiceResponseDTO responseDTO) {
        boolean settled = invoice.getItems().stream().allMatch(item -> item.getSeller().getId().equals(invoice.getSeller().getId()));
        Transaction transaction = new Transaction("",
                responseDTO.getId(), invoice, TransactionType.BITCOIN, responseDTO.getStatus(),
                responseDTO.getTransferredCurrencyAmount(), responseDTO.getUrl(),
                new Date(responseDTO.getUtcTransactionTime()), invoice.getSeller(), settled
        );
        return transactionService.save(transaction);
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

    private ResponseEntity<InvoiceResponseDTO> handlePaymentViaService(Invoice invoice) {
        InvoicePaymentDTO paymentDTO = Converter.invoiceToInvoicePaymentDTO(
                invoice,
                String.format("https://localhost:8080/kp-service/api/invoice/bitcoin/%s", invoice.getId()),
                null, null);

        return bitcoinInvoicePaymentRestTemplate.exchange(
                "https://gateway-service/bitcoin-payment-service/api/payment/", HttpMethod.POST,
                new HttpEntity<>(paymentDTO), InvoiceResponseDTO.class);
    }

    private void notifyClientAboutTransactionUpdate(Invoice invoice, Transaction transaction, boolean isPositiveOutcome) {
        notifyClientStoreAboutTransactionUpdate(invoice, transaction, isPositiveOutcome);
        notifyClientMerchantsAboutTransactionUpdate(invoice, transaction, isPositiveOutcome);
    }

    private void notifyClientStoreAboutTransactionUpdate(Invoice invoice, Transaction transaction, boolean isPositiveOutcome) {
        String url = isPositiveOutcome ? invoice.getSeller().getTransactionSuccessWebhook() :
                                         invoice.getSeller().getTransactionFailureWebhook();
        StoreTransactionDTO transactionDTO = new StoreTransactionDTO(invoice, transaction, TransactionType.BITCOIN);
        postForCustomerWebhook("BitCoinInvoiceController.notifyClientStoreAboutTransactionUpdate", invoice, url, transactionDTO);
    }

    private void notifyClientMerchantsAboutTransactionUpdate(Invoice invoice, Transaction transaction, boolean isPositiveOutcome) {
        Set<InvoiceItem> invoiceItems = invoice.getItems();

        for (InvoiceItem item : invoiceItems) {
            if (!item.getSeller().getId().equals(invoice.getSeller().getId())) {
                // TODO Parallelize
                String url = isPositiveOutcome ? item.getSeller().getTransactionSuccessWebhook() :
                                                 item.getSeller().getTransactionFailureWebhook();
                StoreTransactionDTO transactionDTO = new StoreTransactionDTO(item, transaction, TransactionType.BITCOIN);
                postForCustomerWebhook("BitCoinInvoiceController.notifyClientMerchantsAboutTransactionUpdate", invoice, url, transactionDTO);
            }
        }
    }

    private void postForCustomerWebhook(String service, Invoice invoice, String url, StoreTransactionDTO transactionDTO) {
        try {
            customerWebhookRestTemplate.postForEntity(url, new HttpEntity<>(transactionDTO), Void.class);
        } catch (HttpClientErrorException ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook client error", String.format("Sending data to %s led to: %s", url, ex.getMessage()));
        } catch (HttpServerErrorException ex) {
            logTransaction(service, invoice.getId(), invoice.getSeller().getId(), "customer webhook server error", String.format("Sending data to %s led to: %s", url, ex.getMessage()));
        }
    }

    private ResponseEntity<InvoiceResponseDTO> getInvoiceFromBitcoinService(Invoice invoice) {
        return bitcoinInvoicePaymentRestTemplate.getForEntity(
                String.format("https://gateway-service/bitcoin-payment-service/api/payment/%s/%s",
                        invoice.getSeller().getId(), invoice.getCustomerTransaction().getServiceIssuedId()),
                InvoiceResponseDTO.class);
    }

}
