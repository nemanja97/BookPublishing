package lu.ftn.kpservice.model.dto;

import java.math.BigDecimal;

public class BankInvoiceCreationDTO {

    private String merchantOrderId;

    private String storeId;

    private String successRedirectUrl;

    private String failureRedirectUrl;

    private String errorRedirectUrl;

    private BigDecimal amount;

    private String currency;

    public BankInvoiceCreationDTO() {
    }

    public BankInvoiceCreationDTO(String merchantOrderId, String storeId, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl, BigDecimal amount, String currency) {
        this.merchantOrderId = merchantOrderId;
        this.storeId = storeId;
        this.successRedirectUrl = successRedirectUrl;
        this.failureRedirectUrl = failureRedirectUrl;
        this.errorRedirectUrl = errorRedirectUrl;
        this.amount = amount;
        this.currency = currency;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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
}
