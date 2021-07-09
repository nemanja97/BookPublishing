package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BitcoinServiceAccountInfoDTO {

    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    @NotBlank
    private String token;

    private String pairingCode;

    public BitcoinServiceAccountInfoDTO() {
    }

    public BitcoinServiceAccountInfoDTO(@NotNull @NotBlank String storeId, @NotNull @NotBlank String token, String pairingCode) {
        this.storeId = storeId;
        this.token = token;
        this.pairingCode = pairingCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPairingCode() {
        return pairingCode;
    }

    public void setPairingCode(String pairingCode) {
        this.pairingCode = pairingCode;
    }
}
