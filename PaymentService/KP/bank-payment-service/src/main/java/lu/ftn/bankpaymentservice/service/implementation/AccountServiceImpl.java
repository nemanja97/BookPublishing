package lu.ftn.bankpaymentservice.service.implementation;

import lu.ftn.bankpaymentservice.exception.EntityNotFoundException;
import lu.ftn.bankpaymentservice.model.entity.AccountInfo;
import lu.ftn.bankpaymentservice.repository.AccountInfoRepository;
import lu.ftn.bankpaymentservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountInfoRepository repository;

    @Value("${api.bank1}")
    private String bank1Api;

    @Value("${api.bank2}")
    private String bank2Api;

    @Override
    public AccountInfo get(String storeId) {
        if (storeId == null)
            return null;
        return repository.findById(storeId).orElse(null);
    }

    @Override
    public AccountInfo update(String storeId, String merchantId, String merchantPassword, String bankTransactionEndpoint) {
        AccountInfo info = get(storeId);
        if (info == null) {
            throw new EntityNotFoundException("Account does not exist");
        }

        info.setMerchantId(merchantId);
        info.setMerchantPassword(merchantPassword);
        info.setBankTransactionEndpoint(bankTransactionEndpoint);
        return save(info);
    }

    @Override
    public AccountInfo save(String storeId, String merchantId, String merchantPassword, String bankName) {
        String bankTransactionEndpoint = bankName.equalsIgnoreCase("bank1") ?
                String.format("%s/invoice", bank1Api) :
                String.format("%s/invoice", bank2Api);
        AccountInfo accountInfo = new AccountInfo(storeId, merchantId, merchantPassword, bankTransactionEndpoint);
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
