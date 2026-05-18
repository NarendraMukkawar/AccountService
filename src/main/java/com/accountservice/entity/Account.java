package com.accountservice.entity;

import jakarta.persistence.*;

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
    @Setter
    @Column(unique = true, nullable = false, updatable = false)
    private String accountNumber;


    @Getter
    @Setter
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @Getter
    @Setter
    @Size(max = 50)
    private String middleName;

    @Getter
    @Setter
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @Getter
    @Setter
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Column(unique = true)
    private String mobileNumber;

    @Getter
    @Setter
    @Min(value = 18, message = "Minimum age should be 18")
    @Max(value = 100, message = "Maximum age should be 100")
    private int age;

    @Getter
    @Setter
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal balance;

    @Getter
    @Setter
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;


    // Permanent fields - no setters
    @Getter
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be 12 digits")
    @Column(unique = true, nullable = false, updatable = false)
    private String aadharNumber;

    @Getter
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
    @Column(unique = true, nullable = false, updatable = false)
    private String panNumber;


    public Account() {
    }

    public Account(Long id,
                   String accountNumber,
                   String firstName,
                   String middleName,
                   String lastName,
                   String mobileNumber,
                   int age,
                   BigDecimal balance,
                   String email,
                   AccountType accountType,
                   AccountStatus accountStatus
                   ) {

        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.balance = balance;
        this.email = email;
        this.accountType=accountType;
        this.accountStatus=accountStatus;
    }

    public Account(String accountNumber,
                   String aadharNumber,
                   String panNumber) {
        this.accountNumber=accountNumber;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
    }
}