package lu.ftn.bank1service.model.dto;


import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CardInfoDTO {

    @NotNull(message = "PAN number not present")
    @NotBlank(message = "PAN number not present")
    @Size(min = 16, max = 16, message = "Invalid PAN number")
    @Pattern(regexp = "\\d{16}", message = "Invalid PAN number")
    @CreditCardNumber(message = "Invalid PAN number")
    private String panNumber;

    @NotNull(message = "Card holder name not present")
    @NotBlank(message = "Card holder name not present")
    private String cardHolderName;

    @NotNull(message = "Expiratory date not present")
    @NotBlank(message = "Expiratory date number not present")
    @Size(min = 5, max = 5, message = "Invalid expiratory date")
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "Invalid expiratory date")
    private String expiratoryDate;

    @NotNull(message = "CVC not present")
    @NotBlank(message = "CVC not present")
    @Size(min = 3, max = 3, message = "Invalid CVC number")
    @Pattern(regexp = "\\d{3}", message = "Invalid CVC number")
    private String securityCode;

    public CardInfoDTO() {
    }

    public CardInfoDTO(@NotNull(message = "PAN number not present") @NotBlank(message = "PAN number not present") @Size(min = 16, max = 16, message = "Invalid PAN number") @Pattern(regexp = "\\d{16}", message = "Invalid PAN number") @CreditCardNumber(message = "Invalid PAN number") String panNumber, @NotNull(message = "Card holder name not present") @NotBlank(message = "Card holder name not present") String cardHolderName, @NotNull(message = "Expiratory date not present") @NotBlank(message = "Expiratory date number not present") @Size(min = 5, max = 5, message = "Invalid expiratory date") @Pattern(regexp = "\\d{2}/\\d{2}", message = "Invalid expiratory date") String expiratoryDate, @NotNull(message = "CVC not present") @NotBlank(message = "CVC not present") @Size(min = 3, max = 3, message = "Invalid CVC number") @Pattern(regexp = "\\d{3}", message = "Invalid CVC number") String securityCode) {
        this.panNumber = panNumber;
        this.cardHolderName = cardHolderName;
        this.expiratoryDate = expiratoryDate;
        this.securityCode = securityCode;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiratoryDate() {
        return expiratoryDate;
    }

    public void setExpiratoryDate(String expiratoryDate) {
        this.expiratoryDate = expiratoryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
