package lu.ftn.bankpaymentservice.model.entity;

import lu.ftn.bankpaymentservice.converter.StringCryptoConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountInfo {

    @Id
    private String storeId;

    @Column(nullable = false)
    private String merchantId;

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter.class)
    private String merchantPassword;

    @Column(nullable = false)
    private String bankTransactionEndpoint;

    public AccountInfo() {
    }

    public AccountInfo(String storeId, String merchantId, String merchantPassword, String bankTransactionEndpoint) {
        this.storeId = storeId;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
        this.bankTransactionEndpoint = bankTransactionEndpoint;
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

    public String getBankTransactionEndpoint() {
        return bankTransactionEndpoint;
    }

    public void setBankTransactionEndpoint(String bankTransactionEndpoint) {
        this.bankTransactionEndpoint = bankTransactionEndpoint;
    }
}
