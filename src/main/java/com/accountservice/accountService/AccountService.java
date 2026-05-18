package com.accountservice.accountService;

import com.accountservice.dto.AccountResponseDTO;
import com.accountservice.dto.CreateAccountRequestDTO;
import com.accountservice.dto.UpdateAccountRequestDTO;
import com.accountservice.entity.Account;
import com.accountservice.entity.AccountStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    public AccountResponseDTO createAccount(CreateAccountRequestDTO dto);

    public List<AccountResponseDTO> getAllAccounts();

    public AccountResponseDTO getAccountByAccountNumber(String accountNumber);

    public AccountResponseDTO getAccountByEmail(String email);

    public AccountResponseDTO getAccountByMobileNumber(String mobileNumber);

    public AccountResponseDTO updateAccount(String accountNumber, UpdateAccountRequestDTO updateAccountRequestDTO);

    public Account deposit(String accountNumber, BigDecimal amount);

    public Account withdraw(String accountNumber, BigDecimal amount);

    public Account blockAccount(String accountNumber);

    public BigDecimal getBalance(String accountNumber);

    public Account updateAccountStatus(String AccountNumber, AccountStatus accountStatus);


}
