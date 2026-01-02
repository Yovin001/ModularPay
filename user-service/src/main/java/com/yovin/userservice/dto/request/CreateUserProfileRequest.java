package com.yovin.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateUserProfileRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;
    
    @NotNull(message="Email cannot be null")
    @Email(message="Email should be valid")
    private String email;

    private String firstName;
    private String lastName;
    private String dob; 
    private String address;
    private String phone;

}
