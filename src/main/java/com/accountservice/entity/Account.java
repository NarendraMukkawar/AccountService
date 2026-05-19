package com.accountservice.entity;

import com.accountservice.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String accountNumber;

    @Getter
    @Setter
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Size(max = 50)
    private String middleName;

    @Getter
    @Setter
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String lastName;

    @Getter
    @Setter
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Column(unique = true, nullable = false)
    private String mobileNumber;

    @Getter
    @Setter
    @Min(value = 18, message = "Minimum age should be 18")
    @Max(value = 100, message = "Maximum age should be 100")
    private int age;

    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal balance;

    @Getter
    @Setter
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    // Permanent fields - no setters
    @Getter
    @Setter
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be 12 digits")
    @Column(unique = true, nullable = false, updatable = false)
    private String aadharNumber;

    @Getter
    @Setter
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
    @Column(unique = true, nullable = false, updatable = false)
    private String panNumber;

    // SAVINGS / CURRENT
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    // For CURRENT account overdraft
    @Getter
    @Setter
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit limit cannot be negative")
    private BigDecimal creditLimit;

    @Getter
    @Setter
    @DecimalMin(value = "0.0", inclusive = true, message = "Debit limit cannot be negative")
    private BigDecimal debitLimit;


    // Soft delete / account status
    @Getter
    @Setter
    @Column(nullable = false)
    private boolean active = true;

    public Account() {
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account(Long id, String firstName, String middleName, String lastName, String mobileNumber, int age, BigDecimal balance, String email, String aadharNumber, String panNumber, AccountType accountType, BigDecimal creditLimit, boolean active) {

        this.id = id;
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
        this.active = active;
    }

}