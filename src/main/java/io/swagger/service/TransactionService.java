package io.swagger.service;

import io.swagger.model.entity.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
