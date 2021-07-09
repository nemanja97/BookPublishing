package lu.ftn.bank1service.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PCCRequestDTO {

    private String acquirerOrderId;

    private LocalDateTime acquirerTimeStamp;

    private String panNumber;

    private String cardHolderName;

    private String expiratoryDate;

    private String securityCode;

    private BigDecimal amount;

    private String currency;

    private String toAddress;

    public PCCRequestDTO() {
    }

    public PCCRequestDTO(String acquirerOrderId, LocalDateTime acquirerTimeStamp, String panNumber, String cardHolderName, String expiratoryDate, String securityCode, BigDecimal amount, String currency, String toAddress) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimeStamp = acquirerTimeStamp;
        this.panNumber = panNumber;
        this.cardHolderName = cardHolderName;
        this.expiratoryDate = expiratoryDate;
        this.securityCode = securityCode;
        this.amount = amount;
        this.currency = currency;
        this.toAddress = toAddress;
    }

    public PCCRequestDTO(String acquirerOrderId, String panNumber, String cardHolderName, String expiratoryDate, String securityCode, BigDecimal amount, String currency, String toAddress) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimeStamp = LocalDateTime.now();
        this.panNumber = panNumber;
        this.cardHolderName = cardHolderName;
        this.expiratoryDate = expiratoryDate;
        this.securityCode = securityCode;
        this.amount = amount;
        this.currency = currency;
        this.toAddress = toAddress;
    }

    public String getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(String acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public LocalDateTime getAcquirerTimeStamp() {
        return acquirerTimeStamp;
    }

    public void setAcquirerTimeStamp(LocalDateTime acquirerTimeStamp) {
        this.acquirerTimeStamp = acquirerTimeStamp;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiratoryDate() {
        return expiratoryDate;
    }

    public void setExpiratoryDate(String expiratoryDate) {
        this.expiratoryDate = expiratoryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
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

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
}
