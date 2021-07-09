package lu.ftn.bank1service.repository;

import lu.ftn.bank1service.model.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, String> {

    List<CreditCard> findAllByCardHolderNameAndExpiratoryDate(String cardHolderName, String expiratoryDate);

}
