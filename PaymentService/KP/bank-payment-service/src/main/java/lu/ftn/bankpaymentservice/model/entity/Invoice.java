package lu.ftn.bankpaymentservice.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {

    private String merchantId;

    private String merchantPassword;

    private BigDecimal amount;

    private String currency;

    private String merchantOrderId;

    private LocalDateTime merchantTimestamp;

    private String successRedirectUrl;

    private String failureRedirectUrl;

    private String errorRedirectUrl;

    public Invoice() {
    }

    public Invoice(String merchantId, String merchantPassword, BigDecimal amount, String currency, String merchantOrderId, LocalDateTime merchantTimestamp, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) {
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
        this.amount = amount;
        this.currency = currency;
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
        this.successRedirectUrl = successRedirectUrl;
        this.failureRedirectUrl = failureRedirectUrl;
        this.errorRedirectUrl = errorRedirectUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
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
}
