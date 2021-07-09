package lu.ftn.kpservice.model.dto;

import lu.ftn.kpservice.model.entity.InvoiceItem;

import java.math.BigDecimal;

public class InvoiceItemKPFrontendDTO {

    private String id;

    private String storeIssuedId;

    private String storeId;

    private String name;

    private String description;

    private BigDecimal amount;

    private String currency;

    public InvoiceItemKPFrontendDTO() {
    }

    public InvoiceItemKPFrontendDTO(InvoiceItem invoiceItem) {
        this.id = invoiceItem.getId();
        this.storeIssuedId = invoiceItem.getStoreIssuedId();
        this.storeId = invoiceItem.getSeller().getId();
        this.name = invoiceItem.getName();
        this.description = invoiceItem.getDescription();
        this.amount = invoiceItem.getAmount();
        this.currency = invoiceItem.getCurrency();
    }

    public InvoiceItemKPFrontendDTO(String id, String storeIssuedId, String storeId, String name, String description, BigDecimal amount, String currency) {
        this.id = id;
        this.storeIssuedId = storeIssuedId;
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
