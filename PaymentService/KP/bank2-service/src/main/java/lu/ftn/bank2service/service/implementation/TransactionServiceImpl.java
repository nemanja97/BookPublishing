package lu.ftn.bank2service.service.implementation;

import lu.ftn.bank2service.model.entity.Transaction;
import lu.ftn.bank2service.repository.TransactionRepository;
import lu.ftn.bank2service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }
}
