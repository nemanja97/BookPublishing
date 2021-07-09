package lu.ftn.luaccountingservice.model.dto;

import lu.ftn.luaccountingservice.model.entity.Invoice;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceDTO {

    private String id;

    private String storeId;

    private BigDecimal amount;

    private String currency;

    private String planId;

    private List<InvoiceItemDTO> items;

    public InvoiceDTO() {
    }

    public InvoiceDTO(Invoice invoice, String storeId) {
        this.id = invoice.getId();
        this.storeId = storeId;
        this.amount = invoice.getAmount();
        this.currency = invoice.getCurrency();
        this.items = invoice.getBooks().stream().map(b -> new InvoiceItemDTO(b, storeId)).collect(Collectors.toList());
    }

    public InvoiceDTO(String id, String storeId, BigDecimal amount, String currency, List<InvoiceItemDTO> items) {
        this.id = id;
        this.storeId = storeId;
        this.amount = amount;
        this.currency = currency;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public List<InvoiceItemDTO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
