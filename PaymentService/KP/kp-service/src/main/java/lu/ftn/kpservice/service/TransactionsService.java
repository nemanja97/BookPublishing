package lu.ftn.kpservice.service;

import lu.ftn.kpservice.model.entity.Transaction;

public interface TransactionsService {

    Transaction save(Transaction transaction);

    Transaction getById(String id);

}
