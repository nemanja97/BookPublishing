package lu.ftn.bank2service.controller;

import lu.ftn.bank2service.exception.InvalidMerchantInformationException;
import lu.ftn.bank2service.model.dto.InvoiceDTO;
import lu.ftn.bank2service.model.dto.InvoiceResponseDTO;
import lu.ftn.bank2service.service.InvoiceService;
import lu.ftn.bank2service.model.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/invoice")
@CrossOrigin
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Value("${api.frontend}")
    private String frontendApi;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceDTO dto) {
        try {
            Invoice invoice = invoiceService.createNewInvoice(
                    dto.getMerchantId(), dto.getMerchantPassword(),
                    dto.getAmount(), dto.getCurrency(), dto.getMerchantOrderId(), dto.getMerchantTimestamp(), dto.getSuccessRedirectUrl(), dto.getFailureRedirectUrl(), dto.getErrorRedirectUrl());

            InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO(
                    String.format("%s/payment/%s", frontendApi, invoice.getId()), invoice.getId()
            );
            return new ResponseEntity<>(invoiceResponseDTO, HttpStatus.OK);
        } catch (InvalidMerchantInformationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
