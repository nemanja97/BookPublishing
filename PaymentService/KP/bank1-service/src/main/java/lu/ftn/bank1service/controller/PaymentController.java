package lu.ftn.bank1service.controller;

import lu.ftn.bank1service.exception.*;
import lu.ftn.bank1service.model.dto.*;
import lu.ftn.bank1service.model.entity.Invoice;
import lu.ftn.bank1service.model.entity.Transaction;
import lu.ftn.bank1service.service.InvoiceService;
import lu.ftn.bank1service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/payment")
@CrossOrigin()
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    InvoiceService invoiceService;

    @Bean("kpRestTemplate")
    public RestTemplate kpRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("kpRestTemplate")
    private RestTemplate kpRestTemplate;

    @PutMapping(path = "invoice/{invoiceId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> payInvoice(@Valid @RequestBody CardInfoDTO dto, @PathVariable String invoiceId) {
        try {
            Invoice invoice = paymentService.payInvoice(invoiceId, dto.getPanNumber(), dto.getCardHolderName(), dto.getExpiratoryDate(), dto.getSecurityCode());
            return notifySuccess(invoice);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InsufficientFundsException | InvalidCreditCardException | InvoiceAlreadyPaidException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ClientException ex) {
            return notifyClientException(invoiceId, ex.getMessage());
        } catch (Exception ex) {
            return notifyServerException(invoiceId, ex.getMessage());
        }
    }

    @PutMapping(path = "pcc", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PCCResponseDTO> pccPayInvoice(@Valid @RequestBody PCCRequestDTO dto) {
        Transaction transaction = null;
        PCCResponseDTO responseDTO = null;
        try {
            transaction = paymentService.receiveRequestFromPCC(dto.getAcquirerOrderId(), dto.getAcquirerTimeStamp(),
                    dto.getPanNumber(), dto.getCardHolderName(), dto.getExpiratoryDate(), dto.getSecurityCode(),
                    dto.getAmount(), dto.getCurrency(), dto.getToAddress());
            responseDTO = new PCCResponseDTO(
                    "success", dto.getAcquirerOrderId(), dto.getAcquirerTimeStamp(), transaction.getId(), transaction.getCreateDateTime()
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (InsufficientFundsException e) {
            responseDTO = new PCCResponseDTO(
                    "Insufficient funds", dto.getAcquirerOrderId(), dto.getAcquirerTimeStamp(), null, null
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        } catch (InvalidCreditCardException e) {
            responseDTO = new PCCResponseDTO(
                    "Invalid credit card information", dto.getAcquirerOrderId(), dto.getAcquirerTimeStamp(), null, null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException exception) {
            responseDTO = new PCCResponseDTO(
                    "Account not found", dto.getAcquirerOrderId(), dto.getAcquirerTimeStamp(), null, null
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<RedirectDTO> notifySuccess(Invoice invoice) {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(
                "success", invoice.getMerchantOrderId(), invoice.getId(), invoice.getTransaction().getCreateDateTime(), invoice.getTransaction().getId()
        );
        return kpRestTemplate.postForEntity(invoice.getSuccessRedirectUrl(), responseDTO, RedirectDTO.class);
    }

    private ResponseEntity<RedirectDTO> notifyClientException(String invoiceId, String errorMessage) {
        Invoice invoice = invoiceService.findById(invoiceId);
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(
                errorMessage, invoice.getMerchantOrderId(), invoice.getId(), null, null
        );

        return kpRestTemplate.postForEntity(invoice.getFailureRedirectUrl(), responseDTO, RedirectDTO.class);
    }

    private ResponseEntity<RedirectDTO> notifyServerException(String invoiceId, String errorMessage) {
        Invoice invoice = invoiceService.findById(invoiceId);
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(
                errorMessage, invoice.getMerchantOrderId(), invoice.getId(), null, null
        );

        return kpRestTemplate.postForEntity(invoice.getErrorRedirectUrl(), responseDTO, RedirectDTO.class);
    }
}
