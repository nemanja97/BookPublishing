package lu.ftn.paypalpaymentservice.model.dto;

public class PaymentStatusDTO {

    private String status;

    public PaymentStatusDTO() {
    }

    public PaymentStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
