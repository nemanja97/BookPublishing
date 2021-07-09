package lu.ftn.bitcoinpaymentservice.helper;

import com.bitpay.sdk_light.model.Invoice.Invoice;
import lu.ftn.bitcoinpaymentservice.model.dto.TransactionResponseDTO;

public class Converter {

    public static TransactionResponseDTO transactionResponseDTOfromInvoice(Invoice invoice) {
        return new TransactionResponseDTO(invoice.getId(), invoice.getUrl());
    }
}
