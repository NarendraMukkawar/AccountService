package com.accountservice.dto;

import com.accountservice.entity.AccountStatus;
import com.accountservice.entity.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountResponseDTO {

        private String accountNumber;

        private String firstName;

        private String middleName;

        private String lastName;

        private String mobileNumber;

        private int age;

        private BigDecimal balance;

        private String email;

        private AccountType accountType;

        private AccountStatus accountStatus;
}
