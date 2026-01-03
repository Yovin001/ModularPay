package com.yovin.tnotificationservice.service;

import software.amazon.awssdk.enhanced.dynamodb.*;
import com.yovin.tnotificationservice.entity.Notification;
import com.yovin.tnotificationservice.entity.NotificationType;
import com.yovin.tnotificationservice.entity.NotificationStatus;
import com.yovin.tnotificationservice.dto.request.CreateNotificationRequest;
import com.yovin.tnotificationservice.dto.request.UpdateNotificationStatusRequest;
import org.springframework.http.ResponseEntity;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.yovin.tnotificationservice.dto.response.NotificationResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

       private final DynamoDbEnhancedClient enhancedClient;
    private static final String TABLE_NAME = "notifications";
    
    private DynamoDbTable<Notification> getTable() {
        return enhancedClient.table(TABLE_NAME, TableSchema.fromBean(Notification.class));
    }
    
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        log.info("Creating notification for user: {}", request.getUserId());
        
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .transactionId(request.getTransactionId())
                .notificationType(request.getNotificationType().name()) // ← Convertir a String
                .message(request.getMessage())
                .status(NotificationStatus.PENDING.name()) // ← Convertir a String
                .createdAt(Instant.now().toString())
                .build();
        
        try {
            getTable().putItem(notification);
            log.info("Notification created with ID: {}", notification.getId());
            return mapToResponse(notification);
        } catch (Exception e) {
            log.error("Error creating notification: {}", e.getMessage());
            throw new RuntimeException("Failed to create notification", e);
        }
    }
    
    public NotificationResponse getNotificationById(String id) {
        log.info("Fetching notification by ID: {}", id);
        
        try {
            Key key = Key.builder().partitionValue(id).build();
            Notification notification = getTable().getItem(key);
            
            if (notification == null) {
                throw new NotificationNotFoundException("Notification not found with ID: " + id);
            }
            
            return mapToResponse(notification);
        } catch (ResourceNotFoundException e) {
            throw new NotificationNotFoundException("Notification not found with ID: " + id);
        }
    }
    
    public List<NotificationResponse> getNotificationsByUserId(Long userId) {
        log.info("Fetching notifications for user: {}", userId);
        
        try {
            List<Notification> notifications = getTable().scan().items().stream()
                    .filter(n -> n.getUserId().equals(userId))
                    .collect(Collectors.toList());
            
            return notifications.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching notifications for user {}: {}", userId, e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public NotificationResponse updateNotificationStatus(String id, UpdateNotificationStatusRequest request) {
        log.info("Updating notification {} status to {}", id, request.getStatus());
        
        Key key = Key.builder().partitionValue(id).build();
        Notification notification = getTable().getItem(key);
        
        if (notification == null) {
            throw new NotificationNotFoundException("Notification not found with ID: " + id);
        }
        
        notification.setStatus(request.getStatus().name()); // ← Convertir a String
        getTable().updateItem(notification);
        
        log.info("Notification {} status updated to {}", id, request.getStatus());
        return mapToResponse(notification);
    }
    
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .transactionId(notification.getTransactionId())
                .notificationType(NotificationType.valueOf(notification.getNotificationType())) // ← String a Enum
                .message(notification.getMessage())
                .status(NotificationStatus.valueOf(notification.getStatus())) // ← String a Enum
                .createdAt(notification.getCreatedAt())
                .build();
    }

}
