package lu.ftn.bank1service.service;

import lu.ftn.bank1service.model.entity.CreditCard;

public interface CreditCardService {

    CreditCard save(CreditCard creditCard);

    CreditCard findByCardData(String panNumber, String cardHolderName, String expiratoryDate);

    boolean verifyCreditCard(String panNumber, String cardHolderName, String expiratoryDate, String securityCode);

}
