package com.yovin.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {

    private String firstName;
    private String lastName;
    private String dob; 
    private String address;
    private String phone;
}
