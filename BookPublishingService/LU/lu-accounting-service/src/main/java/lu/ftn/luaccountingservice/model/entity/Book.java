package lu.ftn.luaccountingservice.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Book {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String title;

    private String pictureUrl;

    private BigDecimal price;

    private String currency;

    public Book() {
    }

    public Book(String title, String pictureUrl, BigDecimal price, String currency) {
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.currency = currency;
    }

    public Book(String id, String title, String pictureUrl, BigDecimal price, String currency) {
        this.id = id;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.currency = currency;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Book))
            return false;

        Book other = (Book) obj;
        return id.equals(other.id);
    }

}
