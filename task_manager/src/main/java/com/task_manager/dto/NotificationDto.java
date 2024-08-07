package com.task_manager.dto;

import com.task_manager.entity.Notification;
import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private LocalDateTime creationDate;
    private String message;
    private boolean viewed;
    private Long task;
    private Long user;

    public NotificationDto(Notification notification) {
        id = notification.getId();
        creationDate = notification.getCreationDate();
        message = notification.getMessage();
        if (notification.getTask() != null) task = notification.getTask().getId();
        if (notification.getTask() != null) user = notification.getUser().getId();
    }
}
