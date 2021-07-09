package lu.ftn.paypalpaymentservice.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CustomBillingPlan {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String planId;

    @Column
    private String name;

    @Column
    private String storeId;

    @Column
    private String state;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @Column
    private String description;

    @Column
    private String frequency;

    @Column
    private String frequencyInterval;

    @Column
    private String cycles;

    @Column
    private double amount;

    @Column
    private String currency;

    @Column
    private double amountStart;


    @OneToMany(mappedBy = "billingPlan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CustomAgreement> agreements;

    public CustomBillingPlan() {
    }

    public CustomBillingPlan(String planId, String name, String storeId, String state, String createdAt, String updatedAt, String description, String frequency, String frequencyInterval, String cycles, double amount, String currency, double amountStart) {
        this.planId = planId;
        this.name = name;
        this.storeId = storeId;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.frequency = frequency;
        this.frequencyInterval = frequencyInterval;
        this.cycles = cycles;
        this.amount = amount;
        this.currency = currency;
        this.amountStart = amountStart;
        this.agreements = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequencyInterval() {
        return frequencyInterval;
    }

    public void setFrequencyInterval(String frequencyInterval) {
        this.frequencyInterval = frequencyInterval;
    }

    public String getCycles() {
        return cycles;
    }

    public void setCycles(String cycles) {
        this.cycles = cycles;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmountStart() {
        return amountStart;
    }

    public void setAmountStart(double amountStart) {
        this.amountStart = amountStart;
    }

    public Set<CustomAgreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<CustomAgreement> agreements) {
        this.agreements = agreements;
    }
}
