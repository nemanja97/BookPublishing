package lu.ftn.luaccountingservice.model.dto;

import lu.ftn.luaccountingservice.model.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDTO {

    private String id;

    private String serviceIssuedId;

    private String storeIssuedInvoiceId;

    private TransactionType type;

    private String status;

    private BigDecimal amount;

    private Date utcTransactionTime;

    public TransactionDTO() {
    }

    public TransactionDTO(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type, String status, BigDecimal amount, Date utcTransactionTime) {
        this.id = id;
        this.serviceIssuedId = serviceIssuedId;
        this.storeIssuedInvoiceId = storeIssuedInvoiceId;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.utcTransactionTime = utcTransactionTime;
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
