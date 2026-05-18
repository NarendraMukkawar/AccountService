package com.accountservice.service;

import com.accountservice.dto.AccountRequestDto;
import com.accountservice.dto.AccountResponseDto;
import com.accountservice.dto.UpdateAccountDto;
import com.accountservice.entity.Account;
import com.accountservice.enums.AccountType;
import com.accountservice.repo.AccountRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponseDto createAccount(



            AccountRequestDto accountRequestDto
    ) {

        if (existsByEmail(accountRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (existsByMobileNumber(accountRequestDto.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }

        if (existsByAadharNumber(accountRequestDto.getAadharNumber())) {
            throw new RuntimeException("Aadhar already exists");
        }

        if (existsByPanNumber(accountRequestDto.getPanNumber())) {
            throw new RuntimeException("PAN already exists");
        }

        String accountNumber = generateAccountNumber();

        Account account = new Account(accountNumber);
        account.setFirstName(accountRequestDto.getFirstName());
        account.setMiddleName(accountRequestDto.getMiddleName());
        account.setLastName(accountRequestDto.getLastName());
        account.setMobileNumber(accountRequestDto.getMobileNumber());
        account.setAge(accountRequestDto.getAge());
        account.setBalance(accountRequestDto.getBalance());
        account.setEmail(accountRequestDto.getEmail());
        account.setAccountType(accountRequestDto.getAccountType());
        account.setAadharNumber(accountRequestDto.getAadharNumber());
        account.setPanNumber(accountRequestDto.getPanNumber());

        // Current Account Credit Limit
        if (accountRequestDto.getAccountType() == AccountType.CURRENT) {

            BigDecimal creditLimit = accountRequestDto.getCreditLimit();

            if (creditLimit == null ||
                    creditLimit.compareTo(BigDecimal.valueOf(1000)) < 0) {

                throw new RuntimeException(
                        "Current account must have minimum credit limit of 1000"
                );
            }

            account.setCreditLimit(creditLimit);
        }

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountById(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByEmail(String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByMobileNumber(
            String mobileNumber
    ) {

        Account account = accountRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByAadharNumber(
            String aadharNumber
    ) {

        Account account = accountRepository
                .findByAadharNumber(aadharNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByPanNumber(
            String panNumber
    ) {

        Account account = accountRepository
                .findByPanNumber(panNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public AccountResponseDto updatePersonalDetails(
            Long accountId,
            UpdateAccountDto updateAccountDto
    ) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setFirstName(updateAccountDto.getFirstName());
        account.setMiddleName(updateAccountDto.getMiddleName());
        account.setLastName(updateAccountDto.getLastName());
        account.setMobileNumber(updateAccountDto.getMobileNumber());
        account.setEmail(updateAccountDto.getEmail());

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto depositAmount(
            Long accountId,
            BigDecimal amount
    ) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setBalance(
                account.getBalance().add(amount)
        );

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto withdrawAmount(
            Long accountId,
            BigDecimal amount
    ) {

        if (!canWithdraw(accountId, amount)) {
            throw new RuntimeException("Insufficient balance");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setBalance(
                account.getBalance().subtract(amount)
        );

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public void transferAmount(
            Long senderAccountId,
            Long receiverAccountId,
            BigDecimal amount
    ) {

        withdrawAmount(senderAccountId, amount);

        depositAmount(receiverAccountId, amount);
    }

    @Override
    public void activateAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setActive(true);

        accountRepository.save(account);
    }

    @Override
    public void deactivateAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setActive(false);

        accountRepository.save(account);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByMobileNumber(String mobileNumber) {
        return accountRepository.existsByMobileNumber(mobileNumber);
    }

    @Override
    public boolean existsByAadharNumber(String aadharNumber) {
        return accountRepository.existsByAadharNumber(aadharNumber);
    }

    @Override
    public boolean existsByPanNumber(String panNumber) {
        return accountRepository.existsByPanNumber(panNumber);
    }

    @Override
    public boolean canWithdraw(
            Long accountId,
            BigDecimal amount
    ) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        // Savings Account
        if (account.getAccountType() == AccountType.SAVINGS) {

            return account.getBalance()
                    .compareTo(amount) >= 0;
        }

        // Current Account
        if (account.getAccountType() == AccountType.CURRENT) {

            BigDecimal allowedLimit =
                    account.getBalance()
                            .add(account.getCreditLimit());

            return allowedLimit.compareTo(amount) >= 0;
        }

        return false;
    }

    @Override
    public BigDecimal checkBalance(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return account.getBalance();
    }

    // DTO Mapper
    private AccountResponseDto mapToResponseDto(Account account) {

        return new AccountResponseDto(
                account.getId(),
                account.getFirstName(),
                account.getMiddleName(),
                account.getLastName(),
                account.getMobileNumber(),
                account.getAge(),
                account.getBalance(),
                account.getEmail(),
                maskAadhar(account.getAadharNumber()),
                maskPan(account.getPanNumber()),
                account.getAccountType(),
                account.getCreditLimit(),
                account.isActive()
        );
    }

    private String maskAadhar(String aadhar) {

        if (aadhar == null || aadhar.length() != 12) {
            return null;
        }

        return "XXXXXXXX" + aadhar.substring(8);
    }

    private String maskPan(String pan) {

        return pan.substring(0, 5) +
                "****" +
                pan.substring(9);
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {

            int year = Year.now().getValue();

            Random random = new Random();

            int randomNumber =
                    100000 + random.nextInt(900000);

            accountNumber =
                    "ACC" + year + randomNumber;

        } while (
                accountRepository.existsByAccountNumber(accountNumber)
        );

        return accountNumber;
    }
}