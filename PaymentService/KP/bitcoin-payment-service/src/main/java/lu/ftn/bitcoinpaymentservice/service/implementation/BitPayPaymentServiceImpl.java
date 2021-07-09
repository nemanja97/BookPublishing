package lu.ftn.bitcoinpaymentservice.service.implementation;

import com.bitpay.sdk_light.BitPayException;
import com.bitpay.sdk_light.Client;
import com.bitpay.sdk_light.Env;
import com.bitpay.sdk_light.model.Invoice.Invoice;
import com.bitpay.sdk_light.model.Rate.Rates;
import lu.ftn.bitcoinpaymentservice.exception.EntityNotFoundException;
import lu.ftn.bitcoinpaymentservice.model.entity.AccountInfo;
import lu.ftn.bitcoinpaymentservice.service.AccountService;
import lu.ftn.bitcoinpaymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BitPayPaymentServiceImpl implements PaymentService {

    @Autowired
    AccountService accountService;

    @Override
    public Double getExchangeRate(String currency, String storeId) throws BitPayException {
        AccountInfo info = accountService.get(storeId);
        if (info == null)
            throw new EntityNotFoundException("Account");
        Client bitPayClient = new Client(info.getToken(), Env.Test);

        Rates rates = bitPayClient.getRates();
        Double rate = rates.getRate(currency);
        rates.update();

        return rate;
    }

    @Override
    public Invoice getInvoice(String invoiceId, String storeId) throws BitPayException {
        AccountInfo info = accountService.get(storeId);
        if (info == null)
            throw new EntityNotFoundException("Account");
        Client bitPayClient = new Client(info.getToken(), Env.Test);

        return bitPayClient.getInvoice(invoiceId);
    }

    @Override
    public Invoice createInvoice(String id, String storeId, String name, String successRedirectUrl, BigDecimal amount, String currency) throws BitPayException {
        AccountInfo info = accountService.get(storeId);
        if (info == null)
            throw new EntityNotFoundException("Account");
        Client bitPayClient = new Client(info.getToken(), Env.Test);

        // Reference: https://bitpay.com/api/#rest-api-resources-invoices-create-an-invoice
        Invoice invoice = new Invoice(amount.doubleValue(), currency);

        if (id != null && !id.isEmpty()) {
            invoice.setOrderId(id);
        }

        if (name != null && !name.isEmpty()) {
            invoice.setItemDesc(name);
        }

        if (successRedirectUrl != null && !successRedirectUrl.isEmpty()) {
            invoice.setRedirectURL(successRedirectUrl);
        }

        // Indicates whether items are physical goods.
        // Alternatives include digital goods and services.
        invoice.setPhysical(false);

        // #TODO Enable and test this when deploying
        // URL to which BitPay sends webhook notifications. HTTPS is mandatory.
        // invoice.setNotificationURL("localhost");
        return bitPayClient.createInvoice(invoice);
    }

}
