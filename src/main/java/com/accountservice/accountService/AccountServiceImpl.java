package com.accountservice.accountService;

import com.accountservice.accountRepo.AccountRepository;
import com.accountservice.dto.AccountResponseDTO;
import com.accountservice.dto.CreateAccountRequestDTO;
import com.accountservice.entity.Account;
import com.accountservice.entity.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private  AccountRepository accountRepository;

    @Override
    public AccountResponseDTO createAccount(CreateAccountRequestDTO dto) {

        String accNo=generateAccountNumber();

        Account account=new Account( accNo, dto.getAadharNumber(), dto.getPanNumber());

        account.setFirstName(dto.getFirstName());
        account.setMiddleName(dto.getMiddleName());
        account.setLastName(dto.getLastName());
        account.setMobileNumber(dto.getMobileNumber());
        account.setAge(dto.getAge());
        account.setEmail(dto.getEmail());
        account.setAccountType(dto.getAccountType());

        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(AccountStatus.ACTIVE);

        Account savedAccount=accountRepository.save(account);

        AccountResponseDTO response=new AccountResponseDTO();

        response.setAccountNumber(savedAccount.getAccountNumber());

        response.setFirstName(savedAccount.getFirstName());

        response.setMiddleName(savedAccount.getMiddleName());

        response.setLastName(savedAccount.getLastName());

        response.setMobileNumber(savedAccount.getMobileNumber());

        response.setAge(savedAccount.getAge());

        response.setEmail(savedAccount.getEmail());

        response.setBalance(savedAccount.getBalance());

        response.setAccountType(savedAccount.getAccountType());

        response.setAccountStatus(savedAccount.getAccountStatus());


        return response;

    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        List<Account> accountsList=accountRepository.findAll();

        List<AccountResponseDTO> responseList=new ArrayList<>();

        for(Account account: accountsList){
            AccountResponseDTO response=new AccountResponseDTO();

            response.setAccountNumber(
                    account.getAccountNumber());

            response.setFirstName(
                    account.getFirstName());

            response.setMiddleName(
                    account.getMiddleName());

            response.setLastName(
                    account.getLastName());

            response.setMobileNumber(
                    account.getMobileNumber());

            response.setAge(
                    account.getAge());

            response.setEmail(
                    account.getEmail());

            response.setBalance(
                    account.getBalance());

            response.setAccountType(
                    account.getAccountType());

            response.setAccountStatus(
                    account.getAccountStatus());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public AccountResponseDTO getAccountByAccountNumber(String accountNumber) {

        Optional<Account> optionalAccount=accountRepository.findByAccountNumber(accountNumber);

        if(optionalAccount.isEmpty()){
            throw new RuntimeException("Account Not Found!");
        }

        Account account=optionalAccount.get();

        return mapToResponseDTO(account);
    }

    @Override
    public AccountResponseDTO getAccountByEmail(String email) {

            Optional<Account> optionalAccount=accountRepository.findByEmail(email);

            if(optionalAccount.isEmpty()){
                throw new RuntimeException("Account Not Found!");
            }

            Account account=optionalAccount.get();

            return mapToResponseDTO(account);

    }

    @Override
    public AccountResponseDTO getAccountByMobileNumber(String mobileNumber) {

        Optional<Account> optionalAccount=accountRepository.findByMobileNumber(mobileNumber);

        if(optionalAccount.isEmpty()){
            throw new RuntimeException("Account Not Found!");
        }

        Account account=optionalAccount.get();

        return mapToResponseDTO(account);
    }

    @Override
    public AccountResponseDTO updateAccount(String accountNumber, Account account) {

        return null;
    }

    @Override
    public Account deposit(String accountNumber, BigDecimal amount) {
        return null;
    }

    @Override
    public Account withdraw(String accountNumber, BigDecimal amount) {
        return null;
    }

    @Override
    public Account blockAccount(String accountNumber) {
        return null;
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        return null;
    }

    @Override
    public Account updateAccountStatus(String AccountNumber, AccountStatus accountStatus) {
        return null;
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
                accountRepository
                        .existsByAccountNumber(accountNumber)
        );

        return accountNumber;
    }

    private AccountResponseDTO mapToResponseDTO(Account account) {

        AccountResponseDTO response =
                new AccountResponseDTO();

        response.setAccountNumber(
                account.getAccountNumber());

        response.setFirstName(
                account.getFirstName());

        response.setMiddleName(
                account.getMiddleName());

        response.setLastName(
                account.getLastName());

        response.setMobileNumber(
                account.getMobileNumber());

        response.setAge(
                account.getAge());

        response.setEmail(
                account.getEmail());

        response.setBalance(
                account.getBalance());

        response.setAccountType(
                account.getAccountType());

        response.setAccountStatus(
                account.getAccountStatus());

        return response;
    }
}
