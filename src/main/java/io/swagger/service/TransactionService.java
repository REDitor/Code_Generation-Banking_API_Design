package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.AccountType;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllByIBAN(String iban) {
        return transactionRepository.findAllByIBAN(iban);
    }

    public List<Transaction> getAllByIbanBetweenTimestamps(String iban, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return transactionRepository.findAllByIBANBetweenTimestamps(iban, dateTimeFrom, dateTimeTo);
    }

    public List<Transaction> getAllByUserId(UUID userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public List<Transaction> getAllByUserIdBetweenTimestamps(UUID userId, LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findAllByUserIdBetweenTimeStamps(userId, from, to);
    }

    // returns true if amount is negative of 0
    public boolean isNegativeOrZero(Double amount) {
        return amount <= 0;
    }

    // returns true if accounts are not both of type savings
    public boolean isSavingsAccount(Account account) {
        return account.getType() == AccountType.ACCOUNT_TYPE_SAVINGS;
    }

    // returns true if account owner is same for both accounts
    public boolean hasSameOwner(Account fromAccount, Account toAccount) {
        return fromAccount.getUser().getuserId() == toAccount.getUser().getuserId();
    }



    // returns true if amount is bigger than transaction limit for one transaction
    public boolean exceedsTransactionLimit(Double amount, HttpServletRequest request) {
        User user = userService.getLoggedUser(request);

        return amount > user.getTransactionAmountLimit();
    }

    // returns true if transaction amount + total of current day's transactions (between 00:00 and 23:59) is lower
    public boolean exceedsDailyLimit(Double amount, HttpServletRequest request) {
        User user = userService.getLoggedUser(request);
        List<Transaction> transactions = transactionRepository.findAllByUserIdBetweenTimeStamps(user.getuserId(), LocalDate.now().atTime(0, 0), LocalDate.now().atTime(23, 59));

        Double amountToday = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        return user.getDailyLimit() < (amountToday + amount);
    }

    // returns true if balance is too low
    public boolean exceedsBalance(Account accountFrom, Double amount, HttpServletRequest request) {
        return amount > accountFrom.getBalance();
    }
}
