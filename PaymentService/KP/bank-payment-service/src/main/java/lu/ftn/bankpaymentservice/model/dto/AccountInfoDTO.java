package lu.ftn.bankpaymentservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountInfoDTO {

    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    @NotBlank
    private String merchantId;

    @NotNull
    @NotBlank
    private String merchantPassword;

    @NotNull
    @NotBlank
    private String bankName;

    public AccountInfoDTO() {
    }

    public AccountInfoDTO(@NotNull @NotBlank String storeId, @NotNull @NotBlank String merchantId, @NotNull @NotBlank String merchantPassword, @NotNull @NotBlank String bankName) {
        this.storeId = storeId;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
        this.bankName = bankName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
