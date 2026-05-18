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

    AccountResponseDto getAccountByEmail(String email);

    AccountResponseDto getAccountByMobileNumber(String mobileNumber);

    AccountResponseDto getAccountByAadharNumber(String aadharNumber);

    AccountResponseDto getAccountByPanNumber(String panNumber);

    List<AccountResponseDto> getAllAccounts();

    // Update Operations
    AccountResponseDto updatePersonalDetails(
            Long accountId,
            UpdateAccountDto updateAccountDto
    );

    // Banking Operations
    AccountResponseDto depositAmount(
            Long accountId,
            BigDecimal amount
    );

    AccountResponseDto withdrawAmount(
            Long accountId,
            BigDecimal amount
    );

    void transferAmount(
            Long senderAccountId,
            Long receiverAccountId,
            BigDecimal amount
    );

    // Account Status Operations
    void activateAccount(Long accountId);

    void deactivateAccount(Long accountId);

    // Validation Operations
    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByAadharNumber(String aadharNumber);

    boolean existsByPanNumber(String panNumber);

    // Business Validation
    boolean canWithdraw(
            Long accountId,
            BigDecimal amount
    );

    // Balance Operations
    BigDecimal checkBalance(Long accountId);
}