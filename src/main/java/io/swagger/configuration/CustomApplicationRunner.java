package io.swagger.configuration;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.AccountType;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
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
                new User("Sander", "Harks", LocalDate.parse("1997-12-07"), "Somestreet", 247, "1234AB", "Haarlem",
                        "The Netherlands", 500, 250, Arrays.asList(Role.ROLE_EMPLOYEE), "SanderHarks123", "secret123"),
                new User("Bruno", "Marques", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                        "The Netherlands", 500, 250, Arrays.asList(Role.ROLE_CUSTOMER, Role.ROLE_EMPLOYEE), "BrunoMarques123", "secret123"),
                new User("Pablo", "Guilias", LocalDate.parse("1997-12-07"), "Someotherotherstreet", 321, "2314AB", "Haarlem",
                        "The Netherlands", 500, 250, Arrays.asList(Role.ROLE_CUSTOMER), "PabloGuilias123", "secret123")
        );

        // store users in db
        users.forEach(userService::add);

        // Get users for accounts
        User sander = userRepository.findByUsername("SanderHarks123");
        User bruno = userRepository.findByUsername("BrunoMarques123");
        User pablo = userRepository.findByUsername("PabloGuilias123");

        // store new accounts in db
        accountRepository.save(new Account(accountService.generateIban(), sander, AccountType.ACCOUNT_TYPE_CURRENT, 500, "open", 0));
        accountRepository.save(new Account(accountService.generateIban(), sander, AccountType.ACCOUNT_TYPE_SAVINGS, 10000, "open", 0));
        accountRepository.save(new Account(accountService.generateIban(), bruno, AccountType.ACCOUNT_TYPE_CURRENT, 750, "open", 0));
        accountRepository.save(new Account(accountService.generateIban(), pablo, AccountType.ACCOUNT_TYPE_CURRENT, 1000, "open", 0));

        Account accountSander = accountRepository.findAccountByIBAN("NL01INHO0000000001");
        Account accountSanderSavings = accountRepository.findAccountByIBAN("NL01INHO0000000002");
        Account accountBruno = accountRepository.findAccountByIBAN("NL01INHO0000000003");
        Account accountPablo = accountRepository.findAccountByIBAN("NL01INHO0000000004");

        transactionRepository.save(new Transaction(LocalDateTime.now(), accountSander, accountSanderSavings, 11.23, sander));
        transactionRepository.save(new Transaction(LocalDateTime.now(), accountSander, accountBruno, 11.23, sander));
        transactionRepository.save(new Transaction(LocalDateTime.now(), accountBruno, accountPablo, 11.23, bruno));

        // check if data was stored correctly
        System.out.println("\nUSERS:");
        userRepository.findAll().forEach(System.out::println);

        System.out.println("\nACCOUNTS:");
        accountRepository.findAll().forEach(System.out::println);

        System.out.println("\nTRANSACTIONS:");
        transactionRepository.findAll().forEach(System.out::println);
    }
}
