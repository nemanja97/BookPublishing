package lu.ftn.bank1service.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceDTO {

    @NotNull
    @NotBlank
    private String merchantId;

    @NotNull
    @NotBlank
    private String merchantPassword;

    @Positive
    private BigDecimal amount;

    @NotNull
    @NotBlank
    private String currency;

    @NotNull
    @NotBlank
    private String merchantOrderId;

    @NotNull
    private LocalDateTime merchantTimestamp;

    @NotNull
    @NotBlank
    private String successRedirectUrl;

    @NotNull
    @NotBlank
    private String failureRedirectUrl;

    @NotNull
    @NotBlank
    private String errorRedirectUrl;

    public InvoiceDTO() {
    }

    public InvoiceDTO(@NotNull @NotBlank String merchantId, @NotNull @NotBlank String merchantPassword, @Positive BigDecimal amount, @NotNull @NotBlank String currency, @NotNull @NotBlank String merchantOrderId, @NotNull LocalDateTime merchantTimestamp, @NotNull @NotBlank String successRedirectUrl, @NotNull @NotBlank String failureRedirectUrl, @NotNull @NotBlank String errorRedirectUrl) {
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
