package lu.ftn.bank1service.model.dto;

import java.time.LocalDateTime;

public class PCCResponseDTO {

    private String status;

    private String acquirerOrderId;

    private LocalDateTime acquirerTimestamp;

    private String issuerOrderId;

    private LocalDateTime issuerTimestamp;

    public PCCResponseDTO() {
    }

    public PCCResponseDTO(String status, String acquirerOrderId, LocalDateTime acquirerTimestamp, String issuerOrderId, LocalDateTime issuerTimestamp) {
        this.status = status;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.issuerOrderId = issuerOrderId;
        this.issuerTimestamp = issuerTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(String issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public LocalDateTime getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(LocalDateTime issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
    }
}
