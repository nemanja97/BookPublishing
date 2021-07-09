package lu.ftn.bank1service.service;

import lu.ftn.bank1service.model.entity.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateService {

    ExchangeRate save(ExchangeRate exchangeRate);

    ExchangeRate findByCurrency(String currency);

    BigDecimal getRateForCurrency(String currency);
}
