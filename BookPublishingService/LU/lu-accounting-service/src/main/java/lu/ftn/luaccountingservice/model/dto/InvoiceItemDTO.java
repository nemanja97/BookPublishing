package lu.ftn.luaccountingservice.model.dto;

import lu.ftn.luaccountingservice.model.entity.Book;

import java.math.BigDecimal;

public class InvoiceItemDTO {

    private String id;

    private String storeId;

    private String name;

    private String description;

    private BigDecimal amount;

    private String currency;

    public InvoiceItemDTO() {
    }

    public InvoiceItemDTO(Book book, String storeId) {
        this.id = book.getId();
        this.storeId = storeId;
        this.name = book.getTitle();
        this.description = String.format("%s example description", book.getTitle());
        this.amount = book.getPrice();
        this.currency = book.getCurrency();
    }

    public InvoiceItemDTO(String id, String storeId, String name, String description, BigDecimal amount, String currency) {
        this.id = id;
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
