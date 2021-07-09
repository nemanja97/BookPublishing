package lu.ftn.luaccountingservice.model.dto;

public class ItemDto {
    private Long id;
    private String name;
    private Double numberOfItems;
    private Double unitPrice;

    public ItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Double numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
