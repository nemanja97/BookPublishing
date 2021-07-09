package lu.ftn.bank2service.repository;

import lu.ftn.bank2service.model.entity.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, String> {

    ExchangeRate findByCurrency(String currency);
}
