package com.yovin.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
 
    public Long id;
    public Long userId;
    public String email;
    public String firstName;
    public String lastName;
    public String dob; 
    public String address;
    public String phone;
    public String createdAt;
    public String updatedAt;
}
