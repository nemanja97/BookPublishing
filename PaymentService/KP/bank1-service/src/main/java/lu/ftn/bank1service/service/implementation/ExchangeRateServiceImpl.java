package lu.ftn.bank1service.service.implementation;

import lu.ftn.bank1service.model.entity.ExchangeRate;
import lu.ftn.bank1service.repository.ExchangeRateRepository;
import lu.ftn.bank1service.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    ExchangeRateRepository rateRepository;

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        return rateRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRate findByCurrency(String currency) {
        return rateRepository.findByCurrency(currency);
    }

    @Override
    public BigDecimal getRateForCurrency(String currency) {
        ExchangeRate rate = findByCurrency(currency);
        if (rate == null)
            throw new EntityNotFoundException(String.format("Exchange rate for %s", currency));

        return rate.getRate();
    }
}
