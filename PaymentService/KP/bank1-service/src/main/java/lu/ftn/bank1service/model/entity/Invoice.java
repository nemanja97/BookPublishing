package lu.ftn.bank1service.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    private Account merchantAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String merchantOrderId;

    @Column(nullable = false)
    private LocalDateTime merchantTimestamp;

    @Column(nullable = false)
    private String successRedirectUrl;

    @Column(nullable = false)
    private String failureRedirectUrl;

    @Column(nullable = false)
    private String errorRedirectUrl;

    @OneToOne
    private Transaction transaction;

    @Version
    private Integer version;


    public Invoice() {
    }

    public Invoice(String id, Account merchantAccount, BigDecimal amount, String currency, String merchantOrderId, LocalDateTime merchantTimestamp, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl, Transaction transaction, Integer version) {
        this.id = id;
        this.merchantAccount = merchantAccount;
        this.amount = amount;
        this.currency = currency;
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
        this.successRedirectUrl = successRedirectUrl;
        this.failureRedirectUrl = failureRedirectUrl;
        this.errorRedirectUrl = errorRedirectUrl;
        this.transaction = transaction;
        this.version = version;
    }

    public Invoice(Account merchantAccount, BigDecimal amount, String currency, String merchantOrderId, LocalDateTime merchantTimestamp, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) {
        this.merchantAccount = merchantAccount;
        this.amount = amount;
        this.currency = currency;
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
        this.successRedirectUrl = successRedirectUrl;
        this.failureRedirectUrl = failureRedirectUrl;
        this.errorRedirectUrl = errorRedirectUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(Account merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public LocalDateTime getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(LocalDateTime merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public String getSuccessRedirectUrl() {
        return successRedirectUrl;
    }

    public void setSuccessRedirectUrl(String successRedirectUrl) {
        this.successRedirectUrl = successRedirectUrl;
    }

    public String getFailureRedirectUrl() {
        return failureRedirectUrl;
    }

    public void setFailureRedirectUrl(String failureRedirectUrl) {
        this.failureRedirectUrl = failureRedirectUrl;
    }

    public String getErrorRedirectUrl() {
        return errorRedirectUrl;
    }

    public void setErrorRedirectUrl(String errorRedirectUrl) {
        this.errorRedirectUrl = errorRedirectUrl;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
