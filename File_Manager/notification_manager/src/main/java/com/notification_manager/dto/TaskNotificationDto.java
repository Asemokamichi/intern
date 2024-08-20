package com.notification_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskNotificationDto {
    private Long taskId;
    private Long authorId;
    private String title;
    private Long[] recipientIds;
    private Long dontRecipientId;
}
