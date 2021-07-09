package lu.ftn.bank1service.service.implementation;

import lu.ftn.bank1service.exception.InvalidMerchantInformationException;
import lu.ftn.bank1service.model.entity.Account;
import lu.ftn.bank1service.model.entity.Invoice;
import lu.ftn.bank1service.repository.InvoiceRepository;
import lu.ftn.bank1service.service.AccountService;
import lu.ftn.bank1service.service.CreditCardService;
import lu.ftn.bank1service.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditCardService creditCardService;

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice createNewInvoice(String merchantId, String merchantPassword, BigDecimal amount, String currency, String merchantOrderId, LocalDateTime merchantTimestamp, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) throws InvalidMerchantInformationException {
        if (!accountService.validateMerchantInformation(merchantId, merchantPassword))
            throw new InvalidMerchantInformationException();

        Account merchantAccount = accountService.findByMerchantId(merchantId);
        Invoice invoice = new Invoice(merchantAccount, amount, currency, merchantOrderId, merchantTimestamp,
                successRedirectUrl, failureRedirectUrl, errorRedirectUrl);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceRepository.findById(id).orElse(null);
    }

}
