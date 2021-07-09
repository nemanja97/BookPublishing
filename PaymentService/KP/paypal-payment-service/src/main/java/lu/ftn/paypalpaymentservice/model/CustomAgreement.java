package lu.ftn.paypalpaymentservice.model;

import javax.persistence.*;

@Entity
public class CustomAgreement {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String activeAgreementId;

    @Column
    private String startDate;

    @Column
    private String finalPaymentDate;

    @Column
    private String payerEmail;

    @Column
    private String payerId;

    @Column
    private String status;

    @Column(unique = true)
    private String token;

    @Column
    private String storeId;

    @Column
    private String invoiceId;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CustomBillingPlan billingPlan;

    public CustomAgreement() {
    }

    public CustomAgreement(long id, String activeAgreementId, String startDate, String finalPaymentDate, String payerEmail, String payerId, String status, String token, String storeId, CustomBillingPlan billingPlan) {
        this.id = id;
        this.activeAgreementId = activeAgreementId;
        this.startDate = startDate;
        this.finalPaymentDate = finalPaymentDate;
        this.payerEmail = payerEmail;
        this.payerId = payerId;
        this.status = status;
        this.token = token;
        this.storeId = storeId;
        this.billingPlan = billingPlan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActiveAgreementId() {
        return activeAgreementId;
    }

    public void setActiveAgreementId(String activeAgreementId) {
        this.activeAgreementId = activeAgreementId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinalPaymentDate() {
        return finalPaymentDate;
    }

    public void setFinalPaymentDate(String finalPaymentDate) {
        this.finalPaymentDate = finalPaymentDate;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public CustomBillingPlan getBillingPlan() {
        return billingPlan;
    }

    public void setBillingPlan(CustomBillingPlan billingPlan) {
        this.billingPlan = billingPlan;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
