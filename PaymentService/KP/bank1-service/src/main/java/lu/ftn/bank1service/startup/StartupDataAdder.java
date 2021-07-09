package lu.ftn.bank1service.startup;

import lu.ftn.bank1service.model.entity.Account;
import lu.ftn.bank1service.model.entity.CreditCard;
import lu.ftn.bank1service.model.entity.ExchangeRate;
import lu.ftn.bank1service.service.AccountService;
import lu.ftn.bank1service.service.CreditCardService;
import lu.ftn.bank1service.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StartupDataAdder implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (exchangeRateService.findByCurrency("EUR") == null) {
            addExchangeRates();
            addAccounts();
        }
    }

    private void addExchangeRates() {
        ExchangeRate rateEUR = new ExchangeRate("", "EUR", BigDecimal.valueOf(1.0));
        ExchangeRate rateUSD = new ExchangeRate("", "USD", BigDecimal.valueOf(1.22));
        ExchangeRate rateRSD = new ExchangeRate("", "RSD", BigDecimal.valueOf(118));

        exchangeRateService.save(rateEUR);
        exchangeRateService.save(rateUSD);
        exchangeRateService.save(rateRSD);
    }

    private void addAccounts() {
        Account account1 = new Account(BigDecimal.valueOf(100.0), "EUR", "merchant1", encoder.encode("password"));
        Account account2 = new Account(BigDecimal.valueOf(0.0), "EUR", null, null);
        Account account3 = new Account(BigDecimal.valueOf(1000.0), "EUR", null, null);

        accountService.save(account1);
        accountService.save(account2);
        accountService.save(account3);

        CreditCard creditCard_acc2_valid = new CreditCard("John Smith", encoder.encode("4929366689445208"), "02/23", account2);
        creditCardService.save(creditCard_acc2_valid);
        account2.getCreditCards().add(creditCard_acc2_valid);
        accountService.save(account2);

        CreditCard creditCard_acc3_valid = new CreditCard("Jane Smith", encoder.encode("4485082802643159"), "02/23", account3);
        CreditCard creditCard_acc3_invalid = new CreditCard("Jane Smith", encoder.encode("4716973715839614"), "02/20", account3);
        creditCardService.save(creditCard_acc3_valid);
        creditCardService.save(creditCard_acc3_invalid);
        account3.getCreditCards().add(creditCard_acc3_valid);
        account3.getCreditCards().add(creditCard_acc3_invalid);
        accountService.save(account3);
    }
}
