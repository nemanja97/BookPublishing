package lu.ftn.kpservice.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Document
public class PaypalLog {
    @Id
    private String id;

    private Date date;

    private String service;

    private String invoiceId;

    private String merchantId;

    private String status;

    private String description;

    public PaypalLog() {
    }

    public PaypalLog(String service, String invoiceId, String merchantId, String status, String description) {
        this.id = null;
        this.date = new Date();
        this.service = service;
        this.invoiceId = invoiceId;
        this.merchantId = merchantId;
        this.status = status;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
