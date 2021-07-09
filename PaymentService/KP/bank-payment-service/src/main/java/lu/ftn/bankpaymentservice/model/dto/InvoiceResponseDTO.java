package lu.ftn.bankpaymentservice.model.dto;

public class InvoiceResponseDTO {

    private String paymentUrl;

    private String paymentId;

    public InvoiceResponseDTO() {
    }

    public InvoiceResponseDTO(String paymentUrl, String paymentId) {
        this.paymentUrl = paymentUrl;
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
