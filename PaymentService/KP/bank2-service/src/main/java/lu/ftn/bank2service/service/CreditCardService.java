package lu.ftn.bank2service.service;

import lu.ftn.bank2service.model.entity.CreditCard;

public interface CreditCardService {

    CreditCard save(CreditCard creditCard);

    CreditCard findByCardData(String panNumber, String cardHolderName, String expiratoryDate);

    boolean verifyCreditCard(String panNumber, String cardHolderName, String expiratoryDate, String securityCode);

}
