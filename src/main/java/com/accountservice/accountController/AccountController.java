package com.accountservice.accountController;

import com.accountservice.accountService.AccountService;
import com.accountservice.dto.AccountResponseDTO;
import com.accountservice.dto.CreateAccountRequestDTO;
import com.accountservice.entity.Account;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/accounts")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @PostMapping
    public AccountResponseDTO createAccount(@Valid @RequestBody CreateAccountRequestDTO dto){
        return accountService.createAccount(dto);
    }

    @GetMapping
    public List<AccountResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accountNumber/{accountNumber}")
    public AccountResponseDTO getAccountByAccountNumber(@PathVariable String accountNumber){
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/email/{email}")
    public AccountResponseDTO getAccountByEmail(@PathVariable String email){
        return accountService.getAccountByEmail(email);
    }

    @GetMapping("/mobileNumber/{mobileNumber}")
    public AccountResponseDTO getAccountByMobileNumber(@PathVariable String mobileNumber){
        return accountService.getAccountByMobileNumber(mobileNumber);
    }

}
