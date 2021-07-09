package lu.ftn.bank2service.service;

import lu.ftn.bank2service.model.entity.Account;

public interface AccountService {

    Account save(Account account);

    Account findByMerchantId(String id);

    boolean validateMerchantInformation(String merchantId, String merchantPassword);
}
