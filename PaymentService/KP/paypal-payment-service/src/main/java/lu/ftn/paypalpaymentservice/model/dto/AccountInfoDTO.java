package lu.ftn.paypalpaymentservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountInfoDTO {

    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    @NotBlank
    private String clientId;

    @NotNull
    @NotBlank
    private String clientSecret;

    public AccountInfoDTO() {
    }

    public AccountInfoDTO(@NotNull @NotBlank String storeId, @NotNull @NotBlank String clientId, @NotNull @NotBlank String clientSecret) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
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
}
