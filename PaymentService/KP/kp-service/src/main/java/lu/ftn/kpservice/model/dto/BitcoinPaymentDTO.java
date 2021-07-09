package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BitcoinPaymentDTO {

    @NotNull
    @NotBlank
    private String token;

    private String pairingCode;

    public BitcoinPaymentDTO() {
    }

    public BitcoinPaymentDTO(@NotNull @NotBlank String token, String pairingCode) {
        this.token = token;
        this.pairingCode = pairingCode;
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
