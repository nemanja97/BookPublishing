package lu.ftn.kpservice.model.entity;

import lu.ftn.kpservice.model.enums.TransactionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String serviceIssuedId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal amount;

    private String url;

    @Column(nullable = false)
    private Date utcTransactionTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    private boolean settled = false;

    public Transaction() {
    }

    public Transaction(String id, String serviceIssuedId, Invoice invoice, TransactionType type, String status, BigDecimal amount, String url, Date utcTransactionTime, Store store) {
        this.id = id;
        this.serviceIssuedId = serviceIssuedId;
        this.invoice = invoice;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.url = url;
        this.utcTransactionTime = utcTransactionTime;
        this.store = store;
        this.settled = false;
    }

    public Transaction(String id, String serviceIssuedId, Invoice invoice, TransactionType type, String status, BigDecimal amount, String url, Date utcTransactionTime, Store store, boolean settled) {
        this.id = id;
        this.serviceIssuedId = serviceIssuedId;
        this.invoice = invoice;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.url = url;
        this.utcTransactionTime = utcTransactionTime;
        this.store = store;
        this.settled = settled;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUtcTransactionTime() {
        return utcTransactionTime;
    }

    public void setUtcTransactionTime(Date utcTransactionTime) {
        this.utcTransactionTime = utcTransactionTime;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
