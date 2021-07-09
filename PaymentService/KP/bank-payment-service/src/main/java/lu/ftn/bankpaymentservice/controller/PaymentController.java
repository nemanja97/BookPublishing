package lu.ftn.bankpaymentservice.controller;

import lu.ftn.bankpaymentservice.model.dto.InvoiceCreationDTO;
import lu.ftn.bankpaymentservice.model.dto.InvoiceResponseDTO;
import lu.ftn.bankpaymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody InvoiceCreationDTO dto) {
        return paymentService.createBankPaymentFromInvoice(
                dto.getMerchantOrderId(), dto.getStoreId(),
                dto.getSuccessRedirectUrl(), dto.getFailureRedirectUrl(), dto.getErrorRedirectUrl(),
                dto.getAmount(), dto.getCurrency()
        );
    }
}
