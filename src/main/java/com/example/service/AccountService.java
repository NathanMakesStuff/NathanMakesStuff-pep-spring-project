package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account newAccount) {
        
        if(!accountExists(newAccount.getUsername())) {
            Account added = accountRepository.save(newAccount);
            return added;
        } else return null;
    }

    public boolean accountExists(Integer id) {
        return accountRepository.existsById(id);
    }

    public boolean accountExists(String username) {
        return accountRepository.getByUsername(username).isPresent();
    }

    public ResponseEntity<Account> login(Account account) {
        Optional<Account> login = accountRepository.getByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(login.isPresent())
        return ResponseEntity.ok(login.get());
        else return ResponseEntity.status(401).body(null);
    }
}
