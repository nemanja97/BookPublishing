package lu.ftn.kpservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class CustomOrder {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String orderUUID;
    @Column
    private double total;
    @OneToMany
    private Set<Item> items;
    @Column
    private Date timeStamp;
    @Column
    private String MerchantId;
    @Column
    private String successUrl;
    @Column
    private String errorUrl;
    @Column
    private Boolean completed;
    @Column(nullable = true)
    private Boolean successful;

    public CustomOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderUUID() {
        return orderUUID;
    }

    public void setOrderUUID(String orderUUID) {
        this.orderUUID = orderUUID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String apiKey) {
        this.MerchantId = apiKey;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
}
