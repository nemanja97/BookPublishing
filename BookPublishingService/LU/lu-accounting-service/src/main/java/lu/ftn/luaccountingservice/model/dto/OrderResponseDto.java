package lu.ftn.luaccountingservice.model.dto;

public class OrderResponseDto {
    private String OrderUUID;
    private Boolean successful;
    private String errorCode;

    public OrderResponseDto(String orderUUID, Boolean successful, String errorCode) {
        OrderUUID = orderUUID;
        this.successful = successful;
        this.errorCode = errorCode;
    }

    public String getOrderUUID() {
        return OrderUUID;
    }

    public void setOrderUUID(String orderUUID) {
        OrderUUID = orderUUID;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
