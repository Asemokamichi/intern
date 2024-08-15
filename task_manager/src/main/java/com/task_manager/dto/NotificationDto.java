package com.task_manager.dto;

import com.task_manager.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public NotificationDto(Task task) {
        taskId = task.getId();
        authorId = task.getAuthorId();
        title = task.getTitle();
        recipientIds = task.getResponsibles();
    }
    public NotificationDto(Task task, Long id) {
        this(task);
        dontRecipientId = id;
    }


}
