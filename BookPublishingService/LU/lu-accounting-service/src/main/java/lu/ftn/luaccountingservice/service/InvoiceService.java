package lu.ftn.luaccountingservice.service;

import lu.ftn.luaccountingservice.model.dto.MembershipDTO;
import lu.ftn.luaccountingservice.model.entity.Invoice;
import lu.ftn.luaccountingservice.model.enums.TransactionType;

import java.util.Date;
import java.util.List;

public interface InvoiceService {
    Invoice save(Invoice invoice);

    Invoice findById(String id);

    void markInvoicePaymentAsValid(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type,
                                   String status, Date utcTransactionTime) throws Exception;

    void markInvoicePaymentAsInvalid(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type,
                                     String status, Date utcTransactionTime) throws Exception;

    Invoice createInvoice(List<String> bookIds, String userId);

    Invoice createInvoiceForMembership(MembershipDTO dto, String userId);

    void setMembershipAsExpired();
}
