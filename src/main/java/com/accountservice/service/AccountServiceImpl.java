package com.accountservice.service;

import com.accountservice.dto.AccountRequestDto;
import com.accountservice.dto.AccountResponseDto;
import com.accountservice.dto.UpdateAccountDto;
import com.accountservice.entity.Account;
import com.accountservice.enums.AccountType;
import com.accountservice.repo.AccountRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
//    private final RepositoryMethodInvocationListener repositoryMethodInvocationListener;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
//        this.repositoryMethodInvocationListener = repositoryMethodInvocationListener;
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {

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

            if (creditLimit == null || creditLimit.compareTo(BigDecimal.valueOf(1000)) < 0) {

                throw new RuntimeException("Current account must have minimum credit limit of 1000");
            }

            account.setCreditLimit(creditLimit);
        }

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountById(Long accountId) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account Not Found!"));
        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByEmail(String email) {

        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByMobileNumber(String mobileNumber) {

        Account account = accountRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByAadharNumber(String aadharNumber) {

        Account account = accountRepository.findByAadharNumber(aadharNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto getAccountByPanNumber(String panNumber) {

        Account account = accountRepository.findByPanNumber(panNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponseDto(account);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {

        return accountRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public AccountResponseDto updatePersonalDetails(String accountNumber, UpdateAccountDto updateAccountDto) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account Not Found!"));
        if (account.getEmail().equals(updateAccountDto.getEmail()) || account.getMobileNumber().equals(updateAccountDto.getMobileNumber())) {
            return getAccountResponseDto(updateAccountDto, account);
        }
        else if (existsByEmail(updateAccountDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        else if (existsByMobileNumber(updateAccountDto.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }
        else{
            return getAccountResponseDto(updateAccountDto, account);
        }
    }

    @NonNull
    private AccountResponseDto getAccountResponseDto(UpdateAccountDto updateAccountDto, Account account) {
        account.setFirstName(updateAccountDto.getFirstName());
        account.setMiddleName(updateAccountDto.getMiddleName());
        account.setLastName(updateAccountDto.getLastName());
        account.setMobileNumber(updateAccountDto.getMobileNumber());
        account.setEmail(updateAccountDto.getEmail());

        Account savedAccount = accountRepository.save(account);

        return mapToResponseDto(savedAccount);
    }


    @Override
    public AccountResponseDto depositAmount(String accountNumber, BigDecimal amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public AccountResponseDto withdrawAmount(String accountNumber, BigDecimal amount) {

        if (!canWithdraw(accountNumber, amount)) {
            throw new RuntimeException("Insufficient balance");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        return mapToResponseDto(account);
    }

    @Override
    public void transferAmount(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {

        withdrawAmount(senderAccountNumber, amount);

        depositAmount(receiverAccountNumber, amount);
    }

    @Override
    public void activateAccount(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        account.setActive(true);

        accountRepository.save(account);
    }

    @Override
    public void deactivateAccount(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

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
    public boolean canWithdraw(String accountNumber, BigDecimal amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount Must Be Greater Than 0.");
        }


        // Savings Account
        if (account.getAccountType() == AccountType.SAVINGS) {

            account.setDebitLimit(BigDecimal.valueOf(50000));

            if (amount.compareTo(account.getDebitLimit()) > 0) {
                throw new RuntimeException("Amount Exceeds Limit!");
            }
            return account.getBalance().compareTo(amount) >= 0;
        }

        // Current Account
        if (account.getAccountType() == AccountType.CURRENT) {

            account.setDebitLimit(BigDecimal.valueOf(500000));

            account.setCreditLimit(BigDecimal.valueOf(50000));

            BigDecimal allowedAmount = account.getCreditLimit().add(account.getBalance());

            if (amount.compareTo(allowedAmount) > 0) {
                throw new RuntimeException("Insufficient Balance!");
            }

            if (amount.compareTo(account.getDebitLimit()) > 0) {
                throw new RuntimeException("Amount Exceeds Limit!");
            }
            return true;
        }

        return false;
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        return account.getBalance();
    }

    // DTO Mapper
    private AccountResponseDto mapToResponseDto(Account account) {

        return new AccountResponseDto(account.getId(), account.getFirstName(), account.getMiddleName(), account.getLastName(), account.getMobileNumber(), account.getAge(), account.getBalance(), account.getEmail(), maskAadhar(account.getAadharNumber()), maskPan(account.getPanNumber()), account.getAccountType(), account.getCreditLimit(), account.isActive());
    }

    private String maskAadhar(String aadhar) {

        if (aadhar == null || aadhar.length() != 12) {
            return null;
        }

        return "XXXXXXXX" + aadhar.substring(8);
    }

    private String maskPan(String pan) {

        return pan.substring(0, 5) + "****" + pan.substring(9);
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {

            int year = Year.now().getValue();

            Random random = new Random();

            int randomNumber = 100000 + random.nextInt(900000);

            accountNumber = "ACC" + year + randomNumber;

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}