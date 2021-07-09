package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalServiceAccountInfoDTO {

    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    @NotBlank
    private String clientId;

    @NotNull
    @NotBlank
    private String clientSecret;

    private String pairingCode;

    public PaypalServiceAccountInfoDTO(@NotNull @NotBlank String storeId, @NotNull @NotBlank String clientId, @NotNull @NotBlank String clientSecret, String pairingCode) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.pairingCode = pairingCode;
    }

    public PaypalServiceAccountInfoDTO() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getPairingCode() {
        return pairingCode;
    }

    public void setPairingCode(String pairingCode) {
        this.pairingCode = pairingCode;
    }
}
