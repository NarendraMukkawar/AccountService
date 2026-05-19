package com.accountservice.service;

import com.accountservice.dto.AccountRequestDto;
import com.accountservice.dto.AccountResponseDto;
import com.accountservice.dto.UpdateAccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    // Create Account
    AccountResponseDto createAccount(AccountRequestDto accountRequestDto);

    // Find Account Operations
    AccountResponseDto getAccountById(Long accountId);

    AccountResponseDto getAccountByAccountNumber(String accountNumber);

    AccountResponseDto getAccountByEmail(String email);

    AccountResponseDto getAccountByMobileNumber(String mobileNumber);

    AccountResponseDto getAccountByAadharNumber(String aadharNumber);

    AccountResponseDto getAccountByPanNumber(String panNumber);

    List<AccountResponseDto> getAllAccounts();

    // Update Operations
    AccountResponseDto updatePersonalDetails(String accountNumber, UpdateAccountDto updateAccountDto);

    // Banking Operations
    AccountResponseDto depositAmount(String accountNumber, BigDecimal amount);

    AccountResponseDto withdrawAmount(String accountNumber, BigDecimal amount);

    void transferAmount(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount);

    // Account Status Operations
    void activateAccount(String accountNumber);

    void deactivateAccount(String accountNumber);

    // Validation Operations
    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByAadharNumber(String aadharNumber);

    boolean existsByPanNumber(String panNumber);

    // Business Validation
    boolean canWithdraw(String accountNumber, BigDecimal amount);

    // Balance Operations
    BigDecimal checkBalance(String accountNumber);
}