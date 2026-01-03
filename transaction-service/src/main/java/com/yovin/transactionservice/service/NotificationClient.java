package com.yovin.transactionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${notification-service.url}")
    private String notificationServiceUrl;
    
    public void sendTransactionCompleteNotification(Long userId, Long transactionId, Double amount) {
        try {
            String url = notificationServiceUrl + "/api/notifications";
            
            Map<String, Object> notification = new HashMap<>();
            notification.put("userId", userId);
            notification.put("transactionId", transactionId.toString());
            notification.put("notificationType", "TRANSACTION_COMPLETED"); // ← IMPORTANTE: Agregar tipo
            notification.put("message", String.format(
                    "Transaction #%d of $%.2f has been completed successfully", 
                    transactionId, amount));
            
            restTemplate.postForObject(url, notification, Object.class);
            log.info("Sent transaction completed notification for transaction: {}", transactionId);
        } catch (Exception e) {
            log.error("Failed to send notification for transaction {}: {}", transactionId, e.getMessage());
            // No lanzamos excepción para no romper el flujo principal
        }
    }
    
    public void sendHighValueTransactionNotification(Long userId, Long transactionId, Double amount) {
        try {
            String url = notificationServiceUrl + "/api/notifications";
            
            Map<String, Object> notification = new HashMap<>();
            notification.put("userId", userId);
            notification.put("transactionId", transactionId.toString());
            notification.put("notificationType", "HIGH_VALUE_TRANSACTION"); // ← IMPORTANTE: Agregar tipo
            notification.put("message", String.format(
                    "High value transaction of $%.2f detected", 
                    amount));
            
            restTemplate.postForObject(url, notification, Object.class);
            log.info("Sent high value notification for transaction: {}", transactionId);
        } catch (Exception e) {
            log.error("Failed to send high value notification for transaction {}: {}", transactionId, e.getMessage());
        }
    }
}