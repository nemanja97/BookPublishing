package lu.ftn.bank2service.service;

import lu.ftn.bank2service.model.entity.Transaction;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findById(String id);
}
