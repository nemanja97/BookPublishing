package lu.ftn.luaccountingservice.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Membership membership;

    @Column(nullable = false, updatable = false)
    private LocalDateTime utcCreationTime = LocalDateTime.now();

    @OneToOne(mappedBy = "invoice")
    private Transaction transaction;

    @OneToOne()
    private User payer;

    public Invoice() {
    }

    public Invoice(String id, BigDecimal amount, String currency, List<Book> books, LocalDateTime utcCreationTime, Transaction transaction, User payer) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.books = books;
        this.utcCreationTime = utcCreationTime;
        this.transaction = transaction;
        this.payer = payer;
    }

    public Invoice(BigDecimal amount, String currency, List<Book> books, User payer) {
        this.amount = amount;
        this.currency = currency;
        this.books = books;
        this.payer = payer;
        this.utcCreationTime = LocalDateTime.now();
        this.transaction = null;
    }

    public Invoice(BigDecimal amount, String currency, Membership membership, User payer) {
        this.amount = amount;
        this.currency = currency;
        this.membership = membership;
        this.payer = payer;
        this.utcCreationTime = LocalDateTime.now();
        this.transaction = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LocalDateTime getUtcCreationTime() {
        return utcCreationTime;
    }

    public void setUtcCreationTime(LocalDateTime utcCreationTime) {
        this.utcCreationTime = utcCreationTime;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership memberships) {
        this.membership = memberships;
    }
}

