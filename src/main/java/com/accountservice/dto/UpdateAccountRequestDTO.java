package com.accountservice.dto;

import com.accountservice.entity.AccountType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAccountRequestDTO {
    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Min(value = 18, message = "Minimum age should be 18")
    private int age;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

}
