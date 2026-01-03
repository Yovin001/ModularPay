package com.yovin.tnotificationservice.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.yovin.tnotificationservice.entity.NotificationType;
import com.yovin.tnotificationservice.entity.NotificationStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String id;
    private Long userId;
    private Long transactionId;
    private NotificationType notificationType;
    private String message;
    private NotificationStatus status;
    private String createdAt;
}
