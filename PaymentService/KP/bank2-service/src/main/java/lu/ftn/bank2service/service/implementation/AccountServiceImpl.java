package lu.ftn.bank2service.service.implementation;

import lu.ftn.bank2service.model.entity.Account;
import lu.ftn.bank2service.repository.AccountRepository;
import lu.ftn.bank2service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByMerchantId(String id) {
        return accountRepository.findByMerchantId(id);
    }

    @Override
    public boolean validateMerchantInformation(String merchantId, String merchantPassword) {
        Account account = accountRepository.findByMerchantId(merchantId);
        if (account == null)
            return false;

        return passwordEncoder.matches(merchantPassword, account.getMerchantPassword());
    }
}
