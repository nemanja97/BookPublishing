package lu.ftn.luaccountingservice.model.dto;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class MembershipDTO {
    private String id;
    private String membershipName;
    private BigDecimal price;
    private String currency;
    private String frequency;
    private String planId;

    public MembershipDTO() {
    }

    public MembershipDTO(String id, String membershipName, BigDecimal price, String currency, String frequency, String planId) {
        this.id = id;
        this.membershipName = membershipName;
        this.price = price;
        this.currency = currency;
        this.frequency = frequency;
        this.planId = planId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
