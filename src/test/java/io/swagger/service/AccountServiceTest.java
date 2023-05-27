package io.swagger.service;
import io.cucumber.java.bs.A;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void add_validAccount_returnsAccount() {
        // Arrange
        Account account = new Account();

        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account result = accountService.add(account);

        // Assert
        assertEquals(account, result);
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
    void getAccountByIBAN_validIBAN_returnsAccount() {
        // Arrange
        String iban = "dummyIban";
        Account account = new Account();

        when(accountRepository.getAccountByIBAN(iban)).thenReturn(account);

        // Act
        Account result = accountService.getAccountByIBAN(iban);

        // Assert
        assertEquals(account, result);
    }

    @Test
    void generateIban_latestIbanNull_returnsDefaultIban() {
        // Arrange
        String latestIbanNumber = null;

        when(accountRepository.getLatestIban()).thenReturn(latestIbanNumber);

        // Act
        String result = accountService.generateIban();

        // Assert
        assertEquals("NL01INHO0000000001", result);
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
    void generateIban_bankAccountNumberNotMaxValue_returnsUpdatedIban() {
        // Arrange
        String latestIbanNumber = "NL01INHO0000000001";

        when(accountRepository.getLatestIban()).thenReturn(latestIbanNumber);

        // Act
        String result = accountService.generateIban();

        // Assert
        assertEquals("NL01INHO0000000002", result);
    }

    @Test
    void getAccountByName_validName_returnsListOfAccounts() {
        // Arrange
        String name = "John Doe";
        List<Account> accounts = new ArrayList<>();

        when(accountRepository.findAllByName(name)).thenReturn(accounts);

        // Act
        List<Account> result = accountService.getAccountByName(name);

        // Assert
        assertEquals(accounts, result);
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