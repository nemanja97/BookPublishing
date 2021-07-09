package lu.ftn.bitcoinpaymentservice.model.entity;

import lu.ftn.bitcoinpaymentservice.converter.StringCryptoConverter;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountInfo {

    @Id
    private String storeId;

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter.class)
    private String token;

    @Convert(converter = StringCryptoConverter.class)
    private String pairingCode;

    public AccountInfo() {
    }

    public AccountInfo(String storeId, String token, String pairingCode) {
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
