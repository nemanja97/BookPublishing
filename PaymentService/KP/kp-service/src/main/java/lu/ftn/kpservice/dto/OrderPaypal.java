package lu.ftn.kpservice.dto;

public class OrderPaypal {

    private Long orderId;
    private Double total;
    private String currency;
    private String description;

    private String sellerId;
    private String sellerSecret;

    public OrderPaypal() {
    }

    public OrderPaypal(Long orderId, Double total, String currency, String description, String sellerId, String sellerSecret) {
        this.orderId = orderId;
        this.total = total;
        this.currency = currency;
        this.description = description;
        this.sellerId = sellerId;
        this.sellerSecret = sellerSecret;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerSecret() {
        return sellerSecret;
    }

    public void setSellerSecret(String sellerSecret) {
        this.sellerSecret = sellerSecret;
    }
}
