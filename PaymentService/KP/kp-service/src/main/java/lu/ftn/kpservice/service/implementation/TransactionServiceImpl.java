package lu.ftn.kpservice.service.implementation;

import lu.ftn.kpservice.model.entity.Transaction;
import lu.ftn.kpservice.repository.TransactionRepository;
import lu.ftn.kpservice.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionsService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }
}
