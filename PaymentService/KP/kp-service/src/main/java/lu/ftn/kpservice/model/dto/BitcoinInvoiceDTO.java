package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BitcoinInvoiceDTO {

    @NotNull
    private String merchantOrderId;

    private String itemDescription;

    private String serializedOrder;

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    @NotBlank
    private String currencyCode;

    public BitcoinInvoiceDTO() {
    }

    public BitcoinInvoiceDTO(@NotNull String merchantOrderId, String itemDescription, String serializedOrder, @NotNull @Positive Double amount, @NotNull @NotBlank String currencyCode) {
        this.merchantOrderId = merchantOrderId;
        this.itemDescription = itemDescription;
        this.serializedOrder = serializedOrder;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getSerializedOrder() {
        return serializedOrder;
    }

    public void setSerializedOrder(String serializedOrder) {
        this.serializedOrder = serializedOrder;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
