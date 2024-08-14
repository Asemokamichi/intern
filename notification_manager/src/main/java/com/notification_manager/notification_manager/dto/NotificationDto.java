package com.notification_manager.notification_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.config.Task;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long taskId;
    private Long authorId;
    private String title;
    private Long[] recipientIds;
    private Long dontRecipientId;
}
