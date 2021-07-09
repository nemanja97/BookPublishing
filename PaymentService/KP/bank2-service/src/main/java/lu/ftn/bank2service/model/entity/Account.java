package lu.ftn.bank2service.model.entity;

import lu.ftn.bank2service.converter.StringCryptoConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String accountCurrency;

    @Column(unique = true)
    private String merchantId;

    @Convert(converter = StringCryptoConverter.class)
    private String merchantPassword;

    @OneToMany
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany
    private List<Invoice> invoices = new ArrayList<>();

    @Version
    private Integer version;

    public Account() {
    }

    public Account(String id, BigDecimal balance, String accountCurrency, String merchantId, String merchantPassword, Set<CreditCard> creditCards, List<Invoice> invoices, Integer version) {
        this.id = id;
        this.balance = balance;
        this.accountCurrency = accountCurrency;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
        this.creditCards = creditCards;
        this.invoices = invoices;
        this.version = version;
    }

    public Account(BigDecimal balance, String accountCurrency, String merchantId, String merchantPassword) {
        this.balance = balance;
        this.accountCurrency = accountCurrency;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
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

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

