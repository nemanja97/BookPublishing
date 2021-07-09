package lu.ftn.kpservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lu.ftn.kpservice.model.enums.PaymentType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Store {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String name;

    private String transactionSuccessWebhook;

    private String transactionFailureWebhook;

    private String transactionErrorWebhook;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Store parentStore;

    @OneToMany(mappedBy = "parentStore", cascade = CascadeType.ALL)
    private Set<Store> subStores = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToOne(mappedBy = "managedStore")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PaymentTypes", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<PaymentType> chosenPaymentTypes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PaymentType preferredPaymentType;

    public Store() {
    }

    public Store(String id, String name, String transactionSuccessWebhook, String transactionFailureWebhook, String transactionErrorWebhook, Store parentStore, Set<Store> subStores, Set<Transaction> transactions, User user, Set<PaymentType> chosenPaymentTypes, PaymentType preferredPaymentType) {
        this.id = id;
        this.name = name;
        this.transactionSuccessWebhook = transactionSuccessWebhook;
        this.transactionFailureWebhook = transactionFailureWebhook;
        this.transactionErrorWebhook = transactionErrorWebhook;
        this.parentStore = parentStore;
        this.subStores = subStores;
        this.transactions = transactions;
        this.user = user;
        this.chosenPaymentTypes = chosenPaymentTypes;
        this.preferredPaymentType = preferredPaymentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionSuccessWebhook() {
        return transactionSuccessWebhook;
    }

    public void setTransactionSuccessWebhook(String transactionSuccessWebhook) {
        this.transactionSuccessWebhook = transactionSuccessWebhook;
    }

    public String getTransactionFailureWebhook() {
        return transactionFailureWebhook;
    }

    public void setTransactionFailureWebhook(String transactionFailureWebhook) {
        this.transactionFailureWebhook = transactionFailureWebhook;
    }

    public Store getParentStore() {
        return parentStore;
    }

    public void setParentStore(Store parentStore) {
        this.parentStore = parentStore;
    }

    public Set<Store> getSubStores() {
        return subStores;
    }

    public void setSubStores(Set<Store> subStores) {
        this.subStores = subStores;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PaymentType> getChosenPaymentTypes() {
        return chosenPaymentTypes;
    }

    public void setChosenPaymentTypes(Set<PaymentType> chosenPaymentTypes) {
        this.chosenPaymentTypes = chosenPaymentTypes;
    }

    public PaymentType getPreferredPaymentType() {
        return preferredPaymentType;
    }

    public void setPreferredPaymentType(PaymentType preferredPaymentType) {
        this.preferredPaymentType = preferredPaymentType;
    }

    public String getTransactionErrorWebhook() {
        return transactionErrorWebhook;
    }

    public void setTransactionErrorWebhook(String transactionErrorWebhook) {
        this.transactionErrorWebhook = transactionErrorWebhook;
    }
}
