package lu.ftn.bank2service.repository;

import lu.ftn.bank2service.model.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, String> {

    List<CreditCard> findAllByCardHolderNameAndExpiratoryDate(String cardHolderName, String expiratoryDate);

}
