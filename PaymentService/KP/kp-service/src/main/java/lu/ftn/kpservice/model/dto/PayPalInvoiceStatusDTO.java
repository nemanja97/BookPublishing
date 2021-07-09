package lu.ftn.kpservice.model.dto;

public class PayPalInvoiceStatusDTO {

    private String status;

    public PayPalInvoiceStatusDTO() {
        this.status = "success";
    }

    public PayPalInvoiceStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
