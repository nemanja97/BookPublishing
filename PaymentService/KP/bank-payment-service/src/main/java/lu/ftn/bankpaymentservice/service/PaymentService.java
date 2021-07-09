package lu.ftn.bankpaymentservice.service;

import lu.ftn.bankpaymentservice.model.dto.InvoiceResponseDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface PaymentService {

    ResponseEntity<InvoiceResponseDTO> createBankPaymentFromInvoice(String merchantOrderId, String storeId, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl, BigDecimal amount, String currency);

}
