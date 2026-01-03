package com.yovin.transactionservice.service;

import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    public void sendTransactionCompleteNotification(Long user_id, Long transactionId, Double amount) {
        try {

            String url = notificationServiceUrl + "/api/v1/notifications";
            Map<String, Object> notification = new HashMap<>();
            notification.put("userId", user_id);
            notification.put("transactionId", transactionId);
            notification.put("amount", amount);
            notification.put("message", String.format(
                    "Your transaction #%d of amount %.2f has been completed successfully.", transactionId, amount));

            restTemplate.postForEntity(url, notification, Object.class);
            log.info("Sent transaction complete notification for transaction ID: {}", transactionId);
        } catch (Exception e) {
            log.error("Failed to send notification for transaction ID: {}", transactionId, e.getMessage());
            return;
        }
    }
    public void sendHighValueTransactionNotification(Long user_id, Long transactionId, Double amount) {
        try {

            String url = notificationServiceUrl + "/api/v1/notifications";
            Map<String, Object> notification = new HashMap<>();
            notification.put("userId", user_id);
            notification.put("transactionId", transactionId);
            notification.put("amount", amount);
            notification.put("message", String.format(
                    "High value transaction of $%.2f detected.", amount));

            restTemplate.postForEntity(url, notification, Object.class);
            log.info("Sent high value transaction notification for transaction ID: {}", transactionId);
        } catch (Exception e) {
            log.error("Failed to send high value transaction notification for transaction ID: {}", transactionId, e.getMessage());
            return;
        }
    }
}
