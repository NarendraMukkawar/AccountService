package com.accountservice.controller;

import com.accountservice.dto.AccountRequestDto;
import com.accountservice.dto.AccountResponseDto;
import com.accountservice.dto.UpdateAccountDto;
import com.accountservice.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create Account
    @PostMapping
    public AccountResponseDto createAccount(
            @Valid @RequestBody AccountRequestDto accountRequestDto
    ) {
        return accountService.createAccount(accountRequestDto);
    }

    // Get Account By ID
    @GetMapping("/{accountId}")
    public AccountResponseDto getAccountById(
            @PathVariable Long accountId
    ) {
        return accountService.getAccountById(accountId);
    }

    // Get Account By Email
    @GetMapping("/email/{email}")
    public AccountResponseDto getAccountByEmail(
            @PathVariable String email
    ) {
        return accountService.getAccountByEmail(email);
    }

    // Get Account By Mobile Number
    @GetMapping("/mobile/{mobileNumber}")
    public AccountResponseDto getAccountByMobileNumber(
            @PathVariable String mobileNumber
    ) {
        return accountService.getAccountByMobileNumber(mobileNumber);
    }

    // Get Account By Aadhar Number
    @GetMapping("/aadhar/{aadharNumber}")
    public AccountResponseDto getAccountByAadharNumber(
            @PathVariable String aadharNumber
    ) {
        return accountService.getAccountByAadharNumber(aadharNumber);
    }

    // Get Account By PAN Number
    @GetMapping("/pan/{panNumber}")
    public AccountResponseDto getAccountByPanNumber(
            @PathVariable String panNumber
    ) {
        return accountService.getAccountByPanNumber(panNumber);
    }

    // Get All Accounts
    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Update Personal Details
    @PutMapping("/{accountId}")
    public AccountResponseDto updatePersonalDetails(
            @PathVariable Long accountId,
            @Valid @RequestBody UpdateAccountDto updateAccountDto
    ) {
        return accountService.updatePersonalDetails(
                accountId,
                updateAccountDto
        );
    }

    // Deposit Amount
    @PatchMapping("/{accountId}/deposit")
    public AccountResponseDto depositAmount(
            @PathVariable Long accountId,
            @RequestParam BigDecimal amount
    ) {
        return accountService.depositAmount(accountId, amount);
    }

    // Withdraw Amount
    @PatchMapping("/{accountId}/withdraw")
    public AccountResponseDto withdrawAmount(
            @PathVariable Long accountId,
            @RequestParam BigDecimal amount
    ) {
        return accountService.withdrawAmount(accountId, amount);
    }

    // Transfer Amount
    @PatchMapping("/transfer")
    public void transferAmount(
            @RequestParam Long senderAccountId,
            @RequestParam Long receiverAccountId,
            @RequestParam BigDecimal amount
    ) {
        accountService.transferAmount(
                senderAccountId,
                receiverAccountId,
                amount
        );
    }

    // Activate Account
    @PatchMapping("/{accountId}/activate")
    public void activateAccount(
            @PathVariable Long accountId
    ) {
        accountService.activateAccount(accountId);
    }

    // Deactivate Account
    @PatchMapping("/{accountId}/deactivate")
    public void deactivateAccount(
            @PathVariable Long accountId
    ) {
        accountService.deactivateAccount(accountId);
    }

    // Check Balance
    @GetMapping("/{accountId}/balance")
    public BigDecimal checkBalance(
            @PathVariable Long accountId
    ) {
        return accountService.checkBalance(accountId);
    }
}