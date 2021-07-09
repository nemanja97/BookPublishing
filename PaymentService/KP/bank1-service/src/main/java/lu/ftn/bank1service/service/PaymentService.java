package lu.ftn.bank1service.service;

import lu.ftn.bank1service.exception.*;
import lu.ftn.bank1service.model.entity.Invoice;
import lu.ftn.bank1service.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentService {

    Invoice payInvoice(String invoiceId, String panNumber, String cardHolderName, String expiratoryDate, String securityCode) throws InsufficientFundsException, ClientException, ServerException, InvalidCreditCardException, Exception;

    Transaction receiveRequestFromPCC(String acquirerOrderId, LocalDateTime acquirerTimeStamp, String panNumber, String cardHolderName, String expiratoryDate, String securityCode, BigDecimal amount, String currency, String toAddress) throws InsufficientFundsException, InvalidCreditCardException, EntityNotFoundException;
}
