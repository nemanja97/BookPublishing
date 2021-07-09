package lu.ftn.bank2service.service;

import lu.ftn.bank2service.model.entity.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateService {

    ExchangeRate save(ExchangeRate exchangeRate);

    ExchangeRate findByCurrency(String currency);

    BigDecimal getRateForCurrency(String currency);
}
