package lu.ftn.bank1service.repository;

import lu.ftn.bank1service.model.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    Account findByMerchantId(String merchantId);

}
