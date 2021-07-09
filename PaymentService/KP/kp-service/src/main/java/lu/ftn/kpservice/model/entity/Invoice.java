package lu.ftn.kpservice.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String storeIssuedId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store seller;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<InvoiceItem> items = new HashSet<>();

    @OneToOne(mappedBy = "invoice")
    private Transaction customerTransaction;

    @Column
    private String planId;

    public Invoice() {
    }

    public Invoice(String id, String storeIssuedId, BigDecimal amount, String currency, Store seller, Set<InvoiceItem> items, Transaction customerTransaction) {
        this.id = id;
        this.storeIssuedId = storeIssuedId;
        this.amount = amount;
        this.currency = currency;
        this.seller = seller;
        this.items = items;
        this.customerTransaction = customerTransaction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreIssuedId() {
        return storeIssuedId;
    }

    public void setStoreIssuedId(String storeIssuedId) {
        this.storeIssuedId = storeIssuedId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Store getSeller() {
        return seller;
    }

    public void setSeller(Store seller) {
        this.seller = seller;
    }

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> items) {
        this.items = items;
    }

    public Transaction getCustomerTransaction() {
        return customerTransaction;
    }

    public void setCustomerTransaction(Transaction customerTransaction) {
        this.customerTransaction = customerTransaction;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
