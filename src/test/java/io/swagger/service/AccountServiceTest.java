package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void getAll_noAccounts_returnsEmptyList() {
        // Arrange
        List<Account> accounts = new ArrayList<>();

        when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<Account> result = accountService.getAll(new Account());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void generateIban_bankAccountNumberMaxValue_returnsUpdatedIban() {
        // Arrange
        String latestIbanNumber = "NL01INHO9999999999";

        when(accountRepository.getLatestIban()).thenReturn(latestIbanNumber);

        // Act
        String result = accountService.generateIban();

        // Assert
        assertEquals("NL02INHO0000000001", result);
    }

    @Test
    void totalAmountFromAccounts_validUser_returnsTotalAmount() {
        // Arrange
        User user = new User();
        List<Account> accounts = new ArrayList<>();

        Account account1 = new Account();
        account1.setBalance(100.0);
        Account account2 = new Account();
        account2.setBalance(200.0);
        Account account3 = new Account();
        account3.setBalance(300.0);

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        when(accountRepository.findAllByUser(user)).thenReturn(accounts);

        // Act
        Double result = accountService.totalAmountFromAccounts(user);

        // Assert
        assertEquals(600.0, result.doubleValue());
    }
}