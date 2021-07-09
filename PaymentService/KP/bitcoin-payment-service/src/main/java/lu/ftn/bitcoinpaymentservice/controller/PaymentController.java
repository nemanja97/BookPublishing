package lu.ftn.bitcoinpaymentservice.controller;

import com.bitpay.sdk_light.BitPayException;
import com.bitpay.sdk_light.model.Invoice.Invoice;
import lu.ftn.bitcoinpaymentservice.helper.Converter;
import lu.ftn.bitcoinpaymentservice.model.dto.InvoiceCreationDTO;
import lu.ftn.bitcoinpaymentservice.model.dto.InvoiceResponseDTO;
import lu.ftn.bitcoinpaymentservice.model.dto.TransactionResponseDTO;
import lu.ftn.bitcoinpaymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping(value = "/{storeId}/{invoiceId}", produces = "application/json")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(
            @PathVariable String storeId,
            @PathVariable String invoiceId) throws BitPayException {
        Invoice invoice = paymentService.getInvoice(invoiceId, storeId);
        return new ResponseEntity<>(new InvoiceResponseDTO(invoice), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody InvoiceCreationDTO invoiceCreationDTO) throws BitPayException {
        Invoice invoice = paymentService.createInvoice(
                invoiceCreationDTO.getId(), invoiceCreationDTO.getStoreId(),
                invoiceCreationDTO.getName(), invoiceCreationDTO.getSuccessRedirectUrl(),
                invoiceCreationDTO.getAmount(), invoiceCreationDTO.getCurrency()
                );
        return new ResponseEntity<>(new InvoiceResponseDTO(invoice), HttpStatus.OK);
    }
}