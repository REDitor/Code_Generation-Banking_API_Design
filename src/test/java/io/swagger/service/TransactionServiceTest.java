package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    private TransactionService transactionService;
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionService(transactionRepository, userService);
    }


    @Test
    void getAllByIBANReturnsTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        Account account = new Account();
        account.setIBAN("NL01INHO0000000002");
        account.setBalance(1000.00);

        Transaction transaction1 = new Transaction();
        transaction1.setFrom(account);
        Transaction transaction2 = new Transaction();
        transaction2.setFrom(account);

        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAllByIBAN(account.getIBAN())).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllByIBAN(account.getIBAN());

        assertFalse(result.isEmpty());
    }

    @Test
    void getAllByUserId() {
        User user = new User();
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.00);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200.00);
        Transaction transaction3 = new Transaction();
        transaction3.setAmount(300.00);

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        when(transactionRepository.findAllByUserId(user.getuserId())).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllByUserId(user.getuserId());

        assertFalse(result.isEmpty());
    }

    @Test
    void getAllByUserIdBetweenTimestamps() {
        User user = new User();
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.00);
        transaction1.setTimestamp(LocalDateTime.parse("2020-01-01T10:15:30"));

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200.00);
        transaction2.setTimestamp(LocalDateTime.parse("2022-01-02T10:15:30"));

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(300.00);
        transaction1.setTimestamp(LocalDateTime.parse("2022-12-01T10:15:30"));

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        when(transactionRepository.findAllByUserIdBetweenTimeStamps(user.getuserId(), LocalDateTime.parse("2022-01-01T00:00:01"), LocalDateTime.parse("2022-12-31T00:00:01"))).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllByUserIdBetweenTimestamps(user.getuserId(), LocalDateTime.parse("2022-01-01T00:00:01"), LocalDateTime.parse("2022-12-31T00:00:01"));

        assertFalse(result.isEmpty());
    }
}
