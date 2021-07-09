package lu.ftn.bank2service.model.entity;

import lu.ftn.bank2service.converter.StringCryptoConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter.class)
    private String panNumber;

    @Column(nullable = false)
    private String expiratoryDate;

    @ManyToOne
    private Account account;

    public CreditCard() {
    }

    public CreditCard(String id, String cardHolderName, String panNumber, String expiratoryDate, Account account) {
        this.id = id;
        this.cardHolderName = cardHolderName;
        this.panNumber = panNumber;
        this.expiratoryDate = expiratoryDate;
        this.account = account;
    }

    public CreditCard(String cardHolderName, String panNumber, String expiratoryDate, Account account) {
        this.cardHolderName = cardHolderName;
        this.panNumber = panNumber;
        this.expiratoryDate = expiratoryDate;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getExpiratoryDate() {
        return expiratoryDate;
    }

    public void setExpiratoryDate(String expiratoryDate) {
        this.expiratoryDate = expiratoryDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
