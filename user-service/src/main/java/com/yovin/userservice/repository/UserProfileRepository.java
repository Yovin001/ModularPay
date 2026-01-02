package com.yovin.userservice.repository;

import com.yovin.userservice.entity.UserProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

        Optional<UserProfile> findByUserId(Long userId);
        Optional<UserProfile> findByEmail(String email);

        boolean existsByUserId(Long userId);
        boolean existsByEmail(String email);
  
}
