package com.yovin.tnotificationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yovin.tnotificationservice.service.NotificationService;
import com.yovin.tnotificationservice.dto.request.CreateNotificationRequest;
import com.yovin.tnotificationservice.dto.request.UpdateNotificationStatusRequest;
import com.yovin.tnotificationservice.dto.response.NotificationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    /**
     * POST /api/notifications
     * Crear una nueva notificación
     */
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {
        log.info("REST request to create notification for user: {}", request.getUserId());
        NotificationResponse response = notificationService.createNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * GET /api/notifications/{id}
     * Obtener una notificación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String id) {
        log.info("REST request to get notification by ID: {}", id);
        NotificationResponse response = notificationService.getNotificationById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/notifications/user/{userId}
     * Obtener todas las notificaciones de un usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByUserId(
            @PathVariable Long userId) {
        log.info("REST request to get notifications for user: {}", userId);
        List<NotificationResponse> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * PUT /api/notifications/{id}/status
     * Actualizar el estado de una notificación
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<NotificationResponse> updateNotificationStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateNotificationStatusRequest request) {
        log.info("REST request to update notification {} status to {}", id, request.getStatus());
        NotificationResponse response = notificationService.updateNotificationStatus(id, request);
        return ResponseEntity.ok(response);
    }
}