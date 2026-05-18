package com.accountservice.dto;

import com.accountservice.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class AccountResponseDto {

    @Setter
    private Long id;

    @Setter
    private String firstName;

    @Setter
    private String middleName;

    @Setter
    private String lastName;

    @Setter
    private String mobileNumber;

    @Setter
    private int age;

    @Setter
    private BigDecimal balance;

    @Setter
    private String email;

    @Setter
    private String maskedAadhar;

    @Setter
    private String maskedPan;

    @Setter
    private AccountType accountType;

    @Setter
    private BigDecimal creditLimit;

    @Setter
    private boolean active;

    public AccountResponseDto() {
    }

    public AccountResponseDto(Long id,
                              String firstName,
                              String middleName,
                              String lastName,
                              String mobileNumber,
                              int age,
                              BigDecimal balance,
                              String email,
                              String maskedAadhar,
                              String maskedPan,
                              AccountType accountType,
                              BigDecimal creditLimit,
                              boolean active) {

        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.balance = balance;
        this.email = email;
        this.maskedAadhar = maskedAadhar;
        this.maskedPan = maskedPan;
        this.accountType = accountType;
        this.creditLimit = creditLimit;
        this.active = active;
    }
}