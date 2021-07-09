package lu.ftn.luaccountingservice.model.entity;

import lu.ftn.luaccountingservice.model.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String serviceIssuedId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime utcTransactionTime;

    public Transaction() {
    }

    public Transaction(String id, String serviceIssuedId, Invoice invoice, TransactionType type, String status, LocalDateTime utcTransactionTime) {
        this.id = id;
        this.serviceIssuedId = serviceIssuedId;
        this.invoice = invoice;
        this.type = type;
        this.status = status;
        this.utcTransactionTime = utcTransactionTime;
    }

    public Transaction(String id, String serviceIssuedId, Invoice invoice, TransactionType type, String status, Date utcTransactionTime) {
        this.id = id;
        this.serviceIssuedId = serviceIssuedId;
        this.invoice = invoice;
        this.type = type;
        this.status = status;
        this.utcTransactionTime = utcTransactionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public LocalDateTime getUtcTransactionTime() {
        return utcTransactionTime;
    }

    public void setUtcTransactionTime(LocalDateTime utcTransactionTime) {
        this.utcTransactionTime = utcTransactionTime;
    }
}
