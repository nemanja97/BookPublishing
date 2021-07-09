package lu.ftn.bank2service.service.implementation;

import lu.ftn.bank2service.exception.*;
import lu.ftn.bank2service.model.dto.PCCRequestDTO;
import lu.ftn.bank2service.model.dto.PCCResponseDTO;
import lu.ftn.bank2service.model.entity.*;
import lu.ftn.bank2service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    AccountService accountService;

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    TransactionService transactionService;

    @Bean("pccPaymentRestTemplate")
    public RestTemplate pccPaymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("pccPaymentRestTemplate")
    private RestTemplate pccPaymentRestTemplate;

    @Value("${api.pcc}")
    private String pccApi;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Invoice payInvoice(String invoiceId, String panNumber, String cardHolderName, String expiratoryDate, String securityCode) throws Exception {
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice == null)
            throw new EntityNotFoundException("Invoice");

        if (invoice.getTransaction() != null) {
            throw new InvoiceAlreadyPaidException();
        }

        if (!creditCardService.verifyCreditCard(panNumber, cardHolderName, expiratoryDate, securityCode))
            throw new InvalidCreditCardException();

        CreditCard buyerCreditCard = creditCardService.findByCardData(panNumber, cardHolderName, expiratoryDate);
        if (buyerCreditCard == null)
            return sendPaymentRequestToPCC(invoice, panNumber, cardHolderName, expiratoryDate, securityCode);
        else
            return payFromLocalAccount(invoice, buyerCreditCard);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Transaction receiveRequestFromPCC(String acquirerOrderId, LocalDateTime acquirerTimeStamp, String panNumber, String cardHolderName, String expiratoryDate, String securityCode, BigDecimal amount, String currency, String toAddress) throws InsufficientFundsException, InvalidCreditCardException, EntityNotFoundException {
        if (!creditCardService.verifyCreditCard(panNumber, cardHolderName, expiratoryDate, securityCode))
            throw new InvalidCreditCardException();

        CreditCard buyerCreditCard = creditCardService.findByCardData(panNumber, cardHolderName, expiratoryDate);
        if (buyerCreditCard == null)
            throw new EntityNotFoundException("Credit card");

        BigDecimal amountToMoveInEUR = getConvertedPaymentAmount(amount, currency);
        Account buyerAccount = buyerCreditCard.getAccount();

        if (buyerAccount.getBalance().compareTo(amountToMoveInEUR) > 0) {
            buyerAccount.setBalance(
                    buyerAccount.getBalance().subtract(amountToMoveInEUR)
            );
            accountService.save(buyerAccount);

            Transaction transaction = new Transaction(buyerAccount.getId(), toAddress, amount, currency);
            return transactionService.save(transaction);
        } else {
            throw new InsufficientFundsException();
        }

    }

    private Invoice sendPaymentRequestToPCC(Invoice invoice, String panNumber, String cardHolderName, String expiratoryDate, String securityCode) throws Exception {
        Account sellerAccount = invoice.getMerchantAccount();
        BigDecimal amountToMoveInEUR = getConvertedPaymentAmount(invoice);

        PCCRequestDTO requestDTO = new PCCRequestDTO(invoice.getId(), panNumber, cardHolderName, expiratoryDate, securityCode,
                amountToMoveInEUR, "EUR", String.format("%s@bank1", invoice.getMerchantAccount().getId()));

        try {
            ResponseEntity<PCCResponseDTO> response = pccPaymentRestTemplate.exchange(
                    String.format("%s/pcc", pccApi), HttpMethod.POST,
                    new HttpEntity<>(requestDTO), PCCResponseDTO.class);

            Transaction transaction = new Transaction(panNumber, sellerAccount.getMerchantId(), invoice.getAmount(), invoice.getCurrency());
            transaction = transactionService.save(transaction);

            invoice.setTransaction(transaction);
            invoice = invoiceService.save(invoice);

            sellerAccount.setBalance(
                    sellerAccount.getBalance().add(amountToMoveInEUR)
            );
            accountService.save(sellerAccount);

            return invoice;
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new EntityNotFoundException("Account");

            if (ex.getResponseBodyAsString().equals("INSUFFICIENT_FUNDS"))
                throw new InsufficientFundsException();

            throw new ClientException(ex.getResponseBodyAsString());
        } catch (HttpServerErrorException | UnknownHttpStatusCodeException ex) {
            throw new ServerException(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new Exception("Unknown exception");
        }
    }

    private Invoice payFromLocalAccount(Invoice invoice, CreditCard buyerCreditCard) throws InsufficientFundsException {
        Account buyerAccount = buyerCreditCard.getAccount();
        Account sellerAccount = invoice.getMerchantAccount();
        BigDecimal amountToMoveInEUR = getConvertedPaymentAmount(invoice);

        if (buyerAccount.getBalance().compareTo(amountToMoveInEUR) > 0) {
            Transaction transaction = new Transaction(buyerAccount.getId(), sellerAccount.getMerchantId(), invoice.getAmount(), invoice.getCurrency());
            transaction = transactionService.save(transaction);

            invoice.setTransaction(transaction);
            invoice = invoiceService.save(invoice);

            buyerAccount.setBalance(
                    buyerAccount.getBalance().subtract(amountToMoveInEUR)
            );
            accountService.save(buyerAccount);

            sellerAccount.setBalance(
                    sellerAccount.getBalance().add(amountToMoveInEUR)
            );
            accountService.save(sellerAccount);

            return invoice;
        } else {
            throw new InsufficientFundsException();
        }
    }

    private BigDecimal getConvertedPaymentAmount(Invoice invoice) {
        ExchangeRate rate = exchangeRateService.findByCurrency(invoice.getCurrency());
        return invoice.getAmount().divide(rate.getRate(), 10, RoundingMode.FLOOR);
    }

    private BigDecimal getConvertedPaymentAmount(BigDecimal amount, String currency) {
        ExchangeRate rate = exchangeRateService.findByCurrency(currency);
        return amount.divide(rate.getRate(), 10, RoundingMode.FLOOR);
    }

}
