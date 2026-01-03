package com.yovin.tnotificationservice.dto.request;

import javax.management.Notification;
import com.yovin.tnotificationservice.entity.NotificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNotificationStatusRequest {

    @NotNull(message = "Status is required")
    private NotificationStatus status;
    
}