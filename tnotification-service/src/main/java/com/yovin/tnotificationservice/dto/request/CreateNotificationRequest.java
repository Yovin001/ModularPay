package com.yovin.tnotificationservice.dto.request;

import com.yovin.tnotificationservice.entity.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Transaction id is required")
    private Long transactionId;

    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;

    @NotNull(message = "Message is required")
    private String message;
}
