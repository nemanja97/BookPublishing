package lu.ftn.bitcoinpaymentservice.model.dto;

import java.math.BigDecimal;

public class InvoiceCreationDTO {

    private String id;

    private String storeId;

    private String successRedirectUrl;

    private String failureRedirectUrl;

    private BigDecimal amount;

    private String currency;

    private String name;

    private String description;

    public InvoiceCreationDTO() {
    }

    public InvoiceCreationDTO(String id, String storeId, String successRedirectUrl, String failureRedirectUrl, BigDecimal amount, String currency, String name, String description) {
        this.id = id;
        this.storeId = storeId;
        this.successRedirectUrl = successRedirectUrl;
        this.failureRedirectUrl = failureRedirectUrl;
        this.amount = amount;
        this.currency = currency;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
