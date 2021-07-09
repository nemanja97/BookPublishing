package lu.ftn.paypalpaymentservice.service;

import lu.ftn.paypalpaymentservice.model.AccountInfo;
import lu.ftn.paypalpaymentservice.repository.AccountInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountInfoRepository repository;

    public AccountService(AccountInfoRepository repository) {
        this.repository = repository;
    }

    public AccountInfo get(String storeId) {
        if (storeId == null)
            return null;
        return repository.findById(storeId).orElse(null);
    }

    public AccountInfo update(String storeId, String clientId, String clientSecret) {
        AccountInfo info = get(storeId);
        if (info == null) {
            throw new RuntimeException("Account does not exist");
        }

        info.setClientId(clientId);
        info.setClientSecret(clientSecret);
        return save(info);
    }

    public AccountInfo save(String storeId, String clientId, String clientSecret) {
        AccountInfo accountInfo = new AccountInfo(storeId, clientId, clientSecret);
        return save(accountInfo);
    }

    public AccountInfo save(AccountInfo accountInfo) {
        return repository.save(accountInfo);
    }

    public void remove(String storeId) {
        repository.deleteById(storeId);
    }

}
