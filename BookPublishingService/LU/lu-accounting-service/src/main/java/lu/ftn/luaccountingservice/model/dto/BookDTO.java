package lu.ftn.luaccountingservice.model.dto;

import lu.ftn.luaccountingservice.model.entity.Book;

import java.math.BigDecimal;

public class BookDTO {

    private String id;

    private String title;

    private String pictureUrl;

    private boolean owned;

    private BigDecimal price;

    private String currency;

    public BookDTO() {
    }

    public BookDTO(String id, String title, String pictureUrl, boolean owned, BigDecimal price, String currency) {
        this.id = id;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.owned = owned;
        this.price = price;
        this.currency = currency;
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.pictureUrl = book.getPictureUrl();
        this.price = book.getPrice();
        this.currency = book.getCurrency();
        this.owned = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}
