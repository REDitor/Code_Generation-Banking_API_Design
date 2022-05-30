package io.swagger.service;

import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getOne(UUID transactionId) {
        return transactionRepository.getOne(transactionId);
    }

    public List<Transaction> getAll(Pageable page) {
        Page<Transaction> transactionPage = transactionRepository.findAll(page);

        return transactionPage.getContent();
    }
}
