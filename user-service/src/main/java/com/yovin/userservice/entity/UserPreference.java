package com.yovin.userservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_preferences")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // FK lógica

    @Column(name = "preference_key", length = 100)
    private String preferenceKey;

    @Column(name = "preference_value", length = 255)
    private String preferenceValue;

    // Constructors

    public UserPreference() {
    }

    public UserPreference(Long userId, String preferenceKey, String preferenceValue) {
        this.userId = userId;
        this.preferenceKey = preferenceKey;
        this.preferenceValue = preferenceValue;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPreferenceKey() {
        return preferenceKey;
    }

    public void setPreferenceKey(String preferenceKey) {
        this.preferenceKey = preferenceKey;
    }

    public String getPreferenceValue() {
        return preferenceValue;
    }

    public void setPreferenceValue(String preferenceValue) {
        this.preferenceValue = preferenceValue;
    }

}
