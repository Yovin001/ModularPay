package com.yovin.userservice.repository;

import com.yovin.userservice.entity.UserPreference;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

        List<UserPreference> findByUserId(Long userId);
        Optional<UserPreference> findByUserIdAndPreferenceKey(Long userId, String preferenceKey);}
