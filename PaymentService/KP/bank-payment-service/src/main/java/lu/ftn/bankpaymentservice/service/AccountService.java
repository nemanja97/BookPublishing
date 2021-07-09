package lu.ftn.bankpaymentservice.service;

import lu.ftn.bankpaymentservice.model.entity.AccountInfo;

public interface AccountService {

    AccountInfo get(String storeId);

    AccountInfo update(String storeId, String merchantId, String merchantPassword, String bankTransactionEndpoint);

    AccountInfo save(String storeId, String merchantId, String merchantPassword, String bankTransactionEndpoint);

    AccountInfo save(AccountInfo accountInfo);

    void remove(String storeId);
}

