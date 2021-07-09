package lu.ftn.bank2service.repository;

import lu.ftn.bank2service.model.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    Account findByMerchantId(String merchantId);

}
