package lu.ftn.bank1service.service;

import lu.ftn.bank1service.model.entity.Transaction;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findById(String id);
}
