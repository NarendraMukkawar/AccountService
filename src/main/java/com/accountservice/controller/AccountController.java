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
    public AccountResponseDto createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        return accountService.createAccount(accountRequestDto);
    }

    // Get Account By ID
    @GetMapping("/{accountId}")
    public AccountResponseDto getAccountById(@PathVariable Long accountId) {
        return accountService.getAccountById(accountId);
    }

    // Get Account By Email
    @GetMapping("/email/{email}")
    public AccountResponseDto getAccountByEmail(@PathVariable String email) {
        return accountService.getAccountByEmail(email);
    }

    // Get Account By Account Number
    @GetMapping("/accountNumber/{accountNumber}")
    public AccountResponseDto getAccountByAccountNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    // Get Account By Mobile Number
    @GetMapping("/mobile/{mobileNumber}")
    public AccountResponseDto getAccountByMobileNumber(@PathVariable String mobileNumber) {
        return accountService.getAccountByMobileNumber(mobileNumber);
    }

    // Get Account By Aadhar Number
    @GetMapping("/aadhar/{aadharNumber}")
    public AccountResponseDto getAccountByAadharNumber(@PathVariable String aadharNumber) {
        return accountService.getAccountByAadharNumber(aadharNumber);
    }

    // Get Account By PAN Number
    @GetMapping("/pan/{panNumber}")
    public AccountResponseDto getAccountByPanNumber(@PathVariable String panNumber) {
        return accountService.getAccountByPanNumber(panNumber);
    }

    // Get All Accounts
    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Update Personal Details
    @PutMapping("/{accountNumber}")
    public AccountResponseDto updatePersonalDetails(@PathVariable String accountNumber, @Valid @RequestBody UpdateAccountDto updateAccountDto) {
        return accountService.updatePersonalDetails(accountNumber, updateAccountDto);
    }

    // Deposit Amount
    @PatchMapping("/{accountNumber}/credit")
    public AccountResponseDto depositAmount(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return accountService.depositAmount(accountNumber, amount);
    }

    //     Withdraw Amount
    @PatchMapping("/{accountNumber}/debit")
    public AccountResponseDto withdrawAmount(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return accountService.withdrawAmount(accountNumber, amount);
    }

    //     Transfer Amount
    @PatchMapping("/transfer")
    public void transferAmount(@RequestParam String senderAccountNumber, @RequestParam String receiverAccountNumber, @RequestParam BigDecimal amount) {
        accountService.transferAmount(senderAccountNumber, receiverAccountNumber, amount);
    }

    // Activate Account
    @PatchMapping("/{accountNumber}/activate")
    public void activateAccount(@PathVariable String accountNumber) {
        accountService.activateAccount(accountNumber);
    }

    // Deactivate Account
    @PatchMapping("/{accountNumber}/deactivate")
    public void deactivateAccount(@PathVariable String accountNumber) {
        accountService.deactivateAccount(accountNumber);
    }

    //     Check Balance
    @GetMapping("/{accountNumber}/balance")
    public BigDecimal checkBalance(@PathVariable String accountNumber) {
        return accountService.checkBalance(accountNumber);
    }
}