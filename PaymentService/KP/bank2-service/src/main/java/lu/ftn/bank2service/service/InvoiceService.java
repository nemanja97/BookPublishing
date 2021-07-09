package lu.ftn.bank2service.service;

import lu.ftn.bank2service.exception.InvalidMerchantInformationException;
import lu.ftn.bank2service.model.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface InvoiceService {

    Invoice save(Invoice invoice);

    Invoice createNewInvoice(String merchantId, String merchantPassword, BigDecimal amount, String currency, String merchantOrderId, LocalDateTime merchantTimestamp, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) throws InvalidMerchantInformationException;

    Invoice findById(String id);

}
