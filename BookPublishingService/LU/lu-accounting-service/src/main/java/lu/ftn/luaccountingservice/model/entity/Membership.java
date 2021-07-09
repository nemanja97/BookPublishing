package lu.ftn.luaccountingservice.model.entity;

import lu.ftn.luaccountingservice.model.enums.MembershipFrequency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Membership {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private Date startingDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MembershipFrequency frequency;

    @Column
    private String name;

    private BigDecimal price;

    private String currency;

    private boolean expired = false;

    public Membership() {
    }

    public Membership(String id, Date startingDate, MembershipFrequency frequency, String name, BigDecimal price, String currency, boolean expired) {
        this.id = id;
        this.startingDate = startingDate;
        this.frequency = frequency;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.expired = expired;
    }

    public Membership(Date startingDate, MembershipFrequency frequency, String name, BigDecimal price, String currency) {
        this.startingDate = startingDate;
        this.frequency = frequency;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.expired = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public MembershipFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(MembershipFrequency frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
