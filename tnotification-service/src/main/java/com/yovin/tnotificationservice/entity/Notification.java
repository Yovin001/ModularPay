package com.yovin.tnotificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Not;
import com.yovin.tnotificationservice.entity.NotificationType;
import com.yovin.tnotificationservice.entity.NotificationStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Notification {
    private String id;
    private Long userId;
    private Long transactionId;
    private String notificationType; // String en DB
    private String message;
    private String status; // String en DB
    private String createdAt;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationType getNotificationTypeEnum() {
        return NotificationType.valueOf(notificationType);
    }

    public void setNotificationTypeEnum(NotificationType type) {
        this.notificationType = type.name();
    }

    public NotificationStatus getStatusEnum() {
        return NotificationStatus.valueOf(status);
    }

    public void setStatusEnum(NotificationStatus status) {
        this.status = status.name();
    }
}