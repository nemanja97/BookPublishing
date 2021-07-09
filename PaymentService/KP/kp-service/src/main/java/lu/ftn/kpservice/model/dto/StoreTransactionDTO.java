package lu.ftn.kpservice.model.dto;

import lu.ftn.kpservice.model.entity.Invoice;
import lu.ftn.kpservice.model.entity.InvoiceItem;
import lu.ftn.kpservice.model.entity.Transaction;
import lu.ftn.kpservice.model.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class StoreTransactionDTO {

    private String id;

    private String serviceIssuedId;

    private String storeIssuedInvoiceId;

    private TransactionType type;

    private String status;

    private BigDecimal amount;

    private Date utcTransactionTime;

    public StoreTransactionDTO() {
    }

    public StoreTransactionDTO(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type, String status, BigDecimal amount, Date utcTransactionTime) {
        this.id = id;
        this.serviceIssuedId = serviceIssuedId;
        this.storeIssuedInvoiceId = storeIssuedInvoiceId;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.utcTransactionTime = utcTransactionTime;
    }

    public StoreTransactionDTO(Invoice invoice, Transaction transaction, TransactionType type) {
        this.id = transaction.getId();
        this.serviceIssuedId = transaction.getServiceIssuedId();
        this.storeIssuedInvoiceId = invoice.getStoreIssuedId();
        this.type = type;
        this.status = transaction.getStatus();
        this.amount = transaction.getAmount();
        this.utcTransactionTime = transaction.getUtcTransactionTime();
    }

    public StoreTransactionDTO(InvoiceItem invoiceItem, Transaction transaction, TransactionType type) {
        this.id = transaction.getId();
        this.serviceIssuedId = transaction.getServiceIssuedId();
        this.storeIssuedInvoiceId = invoiceItem.getStoreIssuedId();
        this.type = type;
        this.status = transaction.getStatus();
        this.amount = invoiceItem.getAmount();
        this.utcTransactionTime = transaction.getUtcTransactionTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceIssuedId() {
        return serviceIssuedId;
    }

    public void setServiceIssuedId(String serviceIssuedId) {
        this.serviceIssuedId = serviceIssuedId;
    }

    public String getStoreIssuedInvoiceId() {
        return storeIssuedInvoiceId;
    }

    public void setStoreIssuedInvoiceId(String storeIssuedInvoiceId) {
        this.storeIssuedInvoiceId = storeIssuedInvoiceId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getUtcTransactionTime() {
        return utcTransactionTime;
    }

    public void setUtcTransactionTime(Date utcTransactionTime) {
        this.utcTransactionTime = utcTransactionTime;
    }
}
