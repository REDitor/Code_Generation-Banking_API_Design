package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account add(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAll(Account account) {
        return accountRepository.findAll();
    }
}
