package lu.ftn.bitcoinpaymentservice.service.implementation;

import lu.ftn.bitcoinpaymentservice.model.entity.AccountInfo;
import lu.ftn.bitcoinpaymentservice.repository.AccountInfoRepository;
import lu.ftn.bitcoinpaymentservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountInfoRepository repository;

    @Override
    public AccountInfo get(String storeId) {
        if (storeId == null)
            return null;
        return repository.findById(storeId).orElse(null);
    }

    @Override
    public AccountInfo update(String storeId, String token, String pairingCode) {
        AccountInfo info = get(storeId);
        if (info == null) {
            throw new RuntimeException("Account does not exist");
        }

        info.setToken(token);
        info.setPairingCode(pairingCode);
        return save(info);
    }

    @Override
    public AccountInfo save(String storeId, String token, String pairingCode) {
        AccountInfo accountInfo = new AccountInfo(storeId, token, pairingCode);
        return save(accountInfo);
    }

    @Override
    public AccountInfo save(AccountInfo accountInfo) {
        return repository.save(accountInfo);
    }

    @Override
    public void remove(String storeId) {
        repository.deleteById(storeId);
    }
}
