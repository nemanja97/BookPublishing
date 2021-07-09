package lu.ftn.bank2service.service.implementation;

import lu.ftn.bank2service.model.entity.CreditCard;
import lu.ftn.bank2service.repository.CreditCardRepository;
import lu.ftn.bank2service.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    CreditCardRepository cardRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public CreditCard save(CreditCard creditCard) {
        return cardRepository.save(creditCard);
    }

    @Override
    public CreditCard findByCardData(String panNumber, String cardHolderName, String expiratoryDate) {
        List<CreditCard> creditCards = cardRepository.findAllByCardHolderNameAndExpiratoryDate(cardHolderName, expiratoryDate);
        for (CreditCard card : creditCards) {
            if (encoder.matches(panNumber, card.getPanNumber()))
                return card;
        }
        return null;
    }

    @Override
    public boolean verifyCreditCard(String panNumber, String cardHolderName, String expiratoryDate, String securityCode) {
        if (isExpiratoryDateInvalid(expiratoryDate)) return false;

        // Mock making call to card provider (Visa, MasterCard...)
        return true;
    }

    private boolean isExpiratoryDateInvalid(String expiratoryDate) {
        String firstPart = expiratoryDate.substring(0, 2);
        String secondPart = expiratoryDate.substring(3, 5);

        if (Integer.parseInt(firstPart) < 1 && Integer.parseInt(firstPart) > 12)
            return true;
        if (Integer.parseInt(secondPart) < 21)
            return true;
        return false;
    }
    
}
