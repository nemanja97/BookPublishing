package lu.ftn.bank2service.service;

import lu.ftn.bank2service.exception.EntityNotFoundException;
import lu.ftn.bank2service.exception.InsufficientFundsException;
import lu.ftn.bank2service.exception.InvalidCreditCardException;
import lu.ftn.bank2service.model.entity.Invoice;
import lu.ftn.bank2service.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentService {

    Invoice payInvoice(String invoiceId, String panNumber, String cardHolderName, String expiratoryDate, String securityCode) throws Exception;

    Transaction receiveRequestFromPCC(String acquirerOrderId, LocalDateTime acquirerTimeStamp, String panNumber, String cardHolderName, String expiratoryDate, String securityCode, BigDecimal amount, String currency, String toAddress) throws InsufficientFundsException, InvalidCreditCardException, EntityNotFoundException;
}
