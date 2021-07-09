package lu.ftn.paypalpaymentservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PaymentData {
    @Id
    private String paymentId;
    @Column
    private String storeId;
    @Column
    private String invoiceId;

    public PaymentData(String paymentId, String storeId, String invoiceId) {
        this.paymentId = paymentId;
        this.storeId = storeId;
        this.invoiceId = invoiceId;
    }

    public PaymentData() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
