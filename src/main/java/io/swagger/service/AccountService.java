package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    public Account add(Account account) {

        return accountRepository.save(account);
    }

    public List<Account> getAll(Account account) {
        return accountRepository.findAll();
    }

    public Account getAccountByIBAN(String IBAN){
        return accountRepository.getAccountByIBAN(IBAN);
    }


    public String generateIban() {
        String latestIbanNumber = accountRepository.getLatestIban();

        if (latestIbanNumber == null) {
            latestIbanNumber = "NL01INHO0000000000";
        }

        int checkDigits = Integer.parseInt(latestIbanNumber.substring(2, 4));
        long bankAccountNumber = Integer.parseInt(latestIbanNumber.substring(8, 18));;

        if (bankAccountNumber == 9999999999L) {
            bankAccountNumber = 0000000001L;
            checkDigits =+ 1;
        }
        else {
            bankAccountNumber += 1;
        }

        String newIban = "NL"+String.format("%02d", checkDigits)+"INHO"+String.format("%010d", bankAccountNumber);

        return newIban;
    }

    /*public Account getAccountByName(String name) {
        return null;
    }*/

    public Double totalAmountFromAccounts(User user) {
        List<Account> accountsOfUser = accountRepository.findAllByUser(user);

        Double amount = accountsOfUser.stream().mapToDouble(Account::getBalance).sum();

        return amount;
    }
}
