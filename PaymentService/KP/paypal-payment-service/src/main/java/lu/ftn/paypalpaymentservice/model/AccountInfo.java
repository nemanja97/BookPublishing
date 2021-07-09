package lu.ftn.paypalpaymentservice.model;

import lu.ftn.paypalpaymentservice.converter.StringCryptoConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountInfo {
    @Id
    private String storeId;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter.class)
    private String clientSecret;

    public AccountInfo() {
    }

    public AccountInfo(String storeId, String clientId, String clientSecret) {
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
