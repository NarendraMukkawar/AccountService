package com.accountservice.dto;

import com.accountservice.enums.AccountType;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class AccountRequestDto {

    @Setter
    @NotBlank
    private String firstName;

    @Setter
    private String middleName;

    @Setter
    @NotBlank
    private String lastName;

    @Setter
    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;

    @Setter
    @Min(18)
    @Max(100)
    private int age;

    @Setter
    @DecimalMin(value = "0.0")
    private BigDecimal balance;

    @Setter
    @Email
    private String email;

    @Setter
    @Pattern(regexp = "^[0-9]{12}$")
    private String aadharNumber;

    @Setter
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
    private String panNumber;

    @Setter
    private AccountType accountType;

    @Setter
    private BigDecimal creditLimit;

    public AccountRequestDto() {
    }

    public AccountRequestDto(String firstName, String middleName, String lastName, String mobileNumber, int age, BigDecimal balance, String email, String aadharNumber, String panNumber, AccountType accountType, BigDecimal creditLimit) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.balance = balance;
        this.email = email;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
        this.accountType = accountType;
        this.creditLimit = creditLimit;
    }
}