package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public class CreateInvoiceRequestDTO {

    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @NotBlank
    private String currency;

    @NotNull
    @NotEmpty
    private List<InvoiceItemDTO> items;

    public CreateInvoiceRequestDTO() {
    }

    public CreateInvoiceRequestDTO(@NotNull @NotBlank String id, @NotNull @NotBlank String storeId, @NotNull @Positive BigDecimal amount, @NotNull @NotBlank String currency, @NotNull @NotEmpty List<InvoiceItemDTO> items) {
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
}
