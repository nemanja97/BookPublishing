package lu.ftn.kpservice.model.dto;

import lu.ftn.kpservice.model.entity.Invoice;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceKPFrontendDTO {

    private String id;

    private String storeIssuedId;

    private BigDecimal amount;

    private String currency;

    private String storeId;

    private Set<InvoiceItemKPFrontendDTO> items = new HashSet<>();

    public InvoiceKPFrontendDTO() {
    }

    public InvoiceKPFrontendDTO(Invoice invoice) {
        this.id = invoice.getId();
        this.storeIssuedId = invoice.getStoreIssuedId();
        this.amount = invoice.getAmount();
        this.currency = invoice.getCurrency();
        this.storeId = invoice.getSeller().getId();
        this.items = invoice.getItems().stream().map(InvoiceItemKPFrontendDTO::new).collect(Collectors.toSet());
    }

    public InvoiceKPFrontendDTO(String id, String storeIssuedId, BigDecimal amount, String currency, String storeId, Set<InvoiceItemKPFrontendDTO> items) {
        this.id = id;
        this.storeIssuedId = storeIssuedId;
        this.amount = amount;
        this.currency = currency;
        this.storeId = storeId;
        this.items = items;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Set<InvoiceItemKPFrontendDTO> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItemKPFrontendDTO> items) {
        this.items = items;
    }
}
