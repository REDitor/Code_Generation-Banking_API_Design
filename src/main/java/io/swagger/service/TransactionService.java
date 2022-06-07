package io.swagger.service;

import io.swagger.model.entity.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllByIBAN(String iban) {
        return transactionRepository.findAllByIBAN(iban);
    }

    public List<Transaction> getAllByIbanBetweenTimestamps(String iban, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return transactionRepository.findAllByIBANBetweenTimestamps(iban, dateTimeFrom, dateTimeTo);
    }
}
