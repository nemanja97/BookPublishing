package lu.ftn.bank2service.model.dto;

import java.time.LocalDateTime;

public class PaymentResponseDTO {

    private String transactionStatus;

    private String merchantOrderId;

    private String acquirerOrderId;

    private LocalDateTime acquirerTimestamp;

    private String paymentId;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(String transactionStatus, String merchantOrderId, String acquirerOrderId, LocalDateTime acquirerTimestamp, String paymentId) {
        this.transactionStatus = transactionStatus;
        this.merchantOrderId = merchantOrderId;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.paymentId = paymentId;
    }

    public PaymentResponseDTO(String transactionStatus, String merchantOrderId, String acquirerOrderId, String paymentId) {
        this.transactionStatus = transactionStatus;
        this.merchantOrderId = merchantOrderId;
        this.acquirerOrderId = acquirerOrderId;
        this.paymentId = paymentId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(String acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public LocalDateTime getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(LocalDateTime acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
