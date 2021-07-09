package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BankPaymentDTO {

    private String storeId;

    @NotNull
    @NotBlank
    private String bankName;

    @NotNull
    @NotBlank
    private String merchantId;

    @NotNull
    @NotBlank
    private String merchantPassword;

    public BankPaymentDTO() {
    }

    public BankPaymentDTO(String storeId, @NotNull @NotBlank String bankName, @NotNull @NotBlank String merchantId, @NotNull @NotBlank String merchantPassword) {
        this.storeId = storeId;
        this.bankName = bankName;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
}
