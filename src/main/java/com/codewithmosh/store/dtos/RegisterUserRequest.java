package com.codewithmosh.store.dtos;

import com.codewithmosh.store.validation.LowerCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Maximum 255 characters")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Email is invalid")
    @LowerCase // Custom validator
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25,message = "Password must between 6 to 25 characters")
    private String password;
}
