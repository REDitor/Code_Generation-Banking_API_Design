package io.swagger.configuration;

import io.swagger.model.entity.*;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@Profile("!test")
public class CustomApplicationRunner implements ApplicationRunner {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private UserService userService;
    private AccountService accountService;

    private TransactionRepository transactionRepository;

    public CustomApplicationRunner(UserRepository userRepository, AccountRepository accountRepository, AccountService accountService, UserService userService, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create hardcoded users
        List<User> users = Arrays.asList(
                new User("InHolland", "Bank", LocalDate.parse("1997-12-07"), "Somestreet", 247, "1234AB", "Haarlem",
                        "The Netherlands", 5000, 10000, Arrays.asList(Role.ROLE_EMPLOYEE, Role.ROLE_CUSTOMER), "sander@gmail.com", "InHollandBank123", "secret123"),
                new User("Sander", "Harks", LocalDate.parse("1997-12-07"), "Someotherotherstreet", 321, "2314AB", "Haarlem",
                        "The Netherlands", 500, 750, Arrays.asList(Role.ROLE_CUSTOMER), "sander@gmail.com", "SanderHarks123", "secret123"),
                new User("Bruno", "Marques", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                        "The Netherlands", 500, 2000, Arrays.asList(Role.ROLE_EMPLOYEE), "bruno@gmail.com", "BrunoMarques123", "secret123"),
                new User("dummy1", "dummy123", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                        "The Netherlands", 500, 2000, Arrays.asList(Role.ROLE_CUSTOMER), "bruno@gmail.com", "dummyCustomer1", "secret123"),
                new User("dummy2", "dummy123", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                        "The Netherlands", 500, 2000, Arrays.asList(Role.ROLE_CUSTOMER), "bruno@gmail.com", "dummyCustomer2", "secret123"),
                new User("dummy3", "dummy123", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                "The Netherlands", 500, 2000, Arrays.asList(Role.ROLE_CUSTOMER), "bruno@gmail.com", "dummyCustomer3", "secret123")
        );

        // store users in db
        users.forEach(userService::add);

        // Get users for accounts
        User bank = userRepository.findByUsername("InHollandBank123");
        User sander = userRepository.findByUsername("SanderHarks123");
        User bruno = userRepository.findByUsername("BrunoMarques123");

        // store new accounts in db
        accountRepository.save(new Account(accountService.generateIban(), bank, AccountType.ACCOUNT_TYPE_CURRENT, 100000000, "open", 0));
        accountRepository.save(new Account(accountService.generateIban(), sander, AccountType.ACCOUNT_TYPE_CURRENT, 1000, "open", 0));
        accountRepository.save(new Account(accountService.generateIban(), sander, AccountType.ACCOUNT_TYPE_SAVINGS, 10000, "open", 0));
        accountRepository.save(new Account(accountService.generateIban(), bruno, AccountType.ACCOUNT_TYPE_SAVINGS, 10000, "open", 0));

        Account accountBank = accountRepository.findAccountByIBAN("NL01INHO0000000001");
        Account accountSander = accountRepository.findAccountByIBAN("NL01INHO0000000002");
        Account accountSanderSavings = accountRepository.findAccountByIBAN("NL01INHO0000000003");

        transactionRepository.save(new Transaction(LocalDateTime.now(), accountSander, accountSanderSavings, 11.23, sander));
        transactionRepository.save(new Transaction(LocalDateTime.now(), accountSander, accountBank, 11.23, sander));

        // check if data was stored correctly
        System.out.println("\nUSERS:");
        userRepository.findAll().forEach(System.out::println);

        System.out.println("\nACCOUNTS:");
        accountRepository.findAll().forEach(System.out::println);

        System.out.println("\nTRANSACTIONS:");
        transactionRepository.findAll().forEach(System.out::println);
    }
}
