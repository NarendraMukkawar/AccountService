package com.accountservice.service;

import com.accountservice.entity.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account deposit(Long id, Double amount);

    Account withdraw(Long id, Double amount);
}