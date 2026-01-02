package com.yovin.userservice.service.impl;

import com.yovin.userservice.dto.request.CreateUserProfileRequest;
import com.yovin.userservice.dto.response.UserProfileResponse;
import com.yovin.userservice.entity.UserProfile;
import com.yovin.userservice.exception.UserNotFoundException;
import com.yovin.userservice.repository.UserProfileRepository;
import com.yovin.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    
    private final UserProfileRepository userProfileRepository;
    
    @Override
    public UserProfileResponse createProfile(CreateUserProfileRequest request) {
        log.info("Creating profile for userId: {}", request.getUserId());
        
        // Verificar si ya existe un perfil para este usuario
        if (userProfileRepository.existsByUserId(request.getUserId())) {
            throw new IllegalStateException("Profile already exists for user ID: " + request.getUserId());
        }
        
        UserProfile profile = new UserProfile();
        profile.setUserId(request.getUserId());
        profile.setEmail(request.getEmail());
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setAddress(request.getAddress());
        profile.setPhone(request.getPhone());
        
        UserProfile savedProfile = userProfileRepository.save(profile);
        log.info("Profile created successfully with ID: {}", savedProfile.getId());
        
        return mapToResponse(savedProfile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfileById(Long id) {
        log.info("Fetching profile by ID: {}", id);
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Profile not found with ID: " + id));
        return mapToResponse(profile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUserId(Long userId) {
        log.info("Fetching profile by userId: {}", userId);
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("Profile not found for user ID: " + userId));
        return mapToResponse(profile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByEmail(String email) {
        log.info("Fetching profile by email: {}", email);
        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Profile not found for email: " + email));
        return mapToResponse(profile);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserProfileResponse> getAllProfiles() {
        log.info("Fetching all profiles");
        return userProfileRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    
    @Override
    public void deleteProfile(Long userId) {
        log.info("Deleting profile for userId: {}", userId);
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("Profile not found for user ID: " + userId));
        
        userProfileRepository.delete(profile);
        log.info("Profile deleted successfully for userId: {}", userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(Long userId) {
        return userProfileRepository.existsByUserId(userId);
    }
    
    private UserProfileResponse mapToResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .address(profile.getAddress())
                .phone(profile.getPhone())
                .build();
    }
}