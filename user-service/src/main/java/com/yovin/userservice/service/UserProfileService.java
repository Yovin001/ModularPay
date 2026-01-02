package com.yovin.userservice.service;

import java.util.List;

import com.yovin.userservice.dto.request.CreateUserProfileRequest;
import com.yovin.userservice.dto.response.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse createProfile(CreateUserProfileRequest request);
    UserProfileResponse getProfileByUserId(Long userId);
    UserProfileResponse getProfileById(Long id);
    UserProfileResponse getProfileByEmail(String email);
   List<UserProfileResponse> getAllProfiles();
    void deleteProfile(Long userId);
    boolean existsByUserId(Long userId);
}
