package com.accountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAccountDto {

    private String firstName;

    private String middleName;

    private String lastName;

    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;

    @Email
    private String email;

    public UpdateAccountDto() {
    }

    public UpdateAccountDto(String firstName,
                            String middleName,
                            String lastName,
                            String mobileNumber,
                            String email) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }
}