package lu.ftn.bitcoinpaymentservice.service;

import com.bitpay.sdk_light.BitPayException;
import com.bitpay.sdk_light.model.Invoice.Invoice;

import java.math.BigDecimal;

public interface PaymentService {

    Double getExchangeRate(String currency, String storeId) throws BitPayException;

    Invoice getInvoice(String invoiceId, String storeId) throws BitPayException;

    Invoice createInvoice(String id, String storeId, String name, String successRedirectUrl, BigDecimal amount, String currency) throws BitPayException;
}
