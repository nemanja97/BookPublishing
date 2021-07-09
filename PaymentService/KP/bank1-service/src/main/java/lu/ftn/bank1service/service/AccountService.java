package lu.ftn.bank1service.service;

import lu.ftn.bank1service.model.entity.Account;

public interface AccountService {

    Account save(Account account);

    Account findByMerchantId(String id);

    boolean validateMerchantInformation(String merchantId, String merchantPassword);
}
