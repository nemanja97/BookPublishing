package lu.ftn.bitcoinpaymentservice.service;

import lu.ftn.bitcoinpaymentservice.model.entity.AccountInfo;

public interface AccountService {

    AccountInfo get(String storeId);

    AccountInfo update(String storeId, String token, String pairingCode);

    AccountInfo save(String storeId, String token, String pairingCode);

    AccountInfo save(AccountInfo accountInfo);

    void remove(String storeId);
}
