package lu.ftn.paypalpaymentservice.model.dto;

import java.math.BigDecimal;

public class InvoiceResponseDTO {

    private String id;
    private String orderId;
    private String url;
    private String guid;
    private String token;

    private String itemDescription;

    private String status;
    private String exceptionStatus;

    private String currency;
    private Double transferredCurrencyAmount;

    private String transactionCurrency;
    private BigDecimal transferredTransactionCurrencyAmount;

    private Long utcTransactionTime;

    public InvoiceResponseDTO() {
    }

    public InvoiceResponseDTO(String id, String orderId, String url, String guid, String token, String itemDescription, String status, String exceptionStatus, String currency, Double transferredCurrencyAmount, String transactionCurrency, BigDecimal transferredTransactionCurrencyAmount, Long utcTransactionTime) {
        this.id = id;
        this.orderId = orderId;
        this.url = url;
        this.guid = guid;
        this.token = token;
        this.itemDescription = itemDescription;
        this.status = status;
        this.exceptionStatus = exceptionStatus;
        this.currency = currency;
        this.transferredCurrencyAmount = transferredCurrencyAmount;
        this.transactionCurrency = transactionCurrency;
        this.transferredTransactionCurrencyAmount = transferredTransactionCurrencyAmount;
        this.utcTransactionTime = utcTransactionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionStatus() {
        return exceptionStatus;
    }

    public void setExceptionStatus(String exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTransferredCurrencyAmount() {
        return transferredCurrencyAmount;
    }

    public void setTransferredCurrencyAmount(Double transferredCurrencyAmount) {
        this.transferredCurrencyAmount = transferredCurrencyAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public BigDecimal getTransferredTransactionCurrencyAmount() {
        return transferredTransactionCurrencyAmount;
    }

    public void setTransferredTransactionCurrencyAmount(BigDecimal transferredTransactionCurrencyAmount) {
        this.transferredTransactionCurrencyAmount = transferredTransactionCurrencyAmount;
    }

    public Long getUtcTransactionTime() {
        return utcTransactionTime;
    }

    public void setUtcTransactionTime(Long utcTransactionTime) {
        this.utcTransactionTime = utcTransactionTime;
    }
}
