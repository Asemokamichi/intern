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
    private Long dontSendUserId;

    public NotificationDto(Task task) {
        this.taskId = task.getId();
    }
    public NotificationDto(Task task, Long id) {
        this.taskId = task.getId();
        dontSendUserId = id;
    }


}
