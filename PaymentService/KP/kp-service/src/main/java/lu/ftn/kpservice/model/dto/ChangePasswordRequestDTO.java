package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordRequestDTO {

    @NotNull
    @NotBlank
    private String password;

    public ChangePasswordRequestDTO() {
    }

    public ChangePasswordRequestDTO(@NotNull @NotBlank String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
