package com.task_manager.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_manager.dto.NotificationDto;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import com.task_manager.enums.NotificationType;
import com.task_manager.exceptions.AlreadyExists;
import com.task_manager.service.NotificationService;
import com.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @Autowired
    private TaskService taskService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics = "task.assigned", groupId = "my-group")
    public void taskAssignedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyAssignmentOfNewTask(task);
    }

    @KafkaListener(topics = "task.deadline.extended", groupId = "my-group")
    public void taskDeadlineExtendedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyAllAboutDeadlineExtension(task);
    }

    @KafkaListener(topics = "task.deleted", groupId = "my-group")
    public void taskDeletedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyTaskDeleted(task);
    }

    @KafkaListener(topics = "task.completed", groupId = "my-group")
    public void taskCompletedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyTaskCompleted(task);
    }

    @KafkaListener(topics = "task.accepted.started", groupId = "my-group")
    public void taskAcceptedStartedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyManagerAboutTaskAcceptance(task);
    }

    @KafkaListener(topics = "task.resolution.received", groupId = "my-group")
    public void taskResolutionReceivedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyResolutionReceived(task);
    }

    @KafkaListener(topics = "task.resolution.approved", groupId = "my-group")
    public void taskResolutionApprovedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyTaskResolutionApproved(task);
    }

    @KafkaListener(topics = "task.resolution.revision", groupId = "my-group")
    public void taskResolutionRevisionConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyTaskResolutionReturnedForRevision(task);
    }

    @KafkaListener(topics = "task.comment.added", groupId = "my-group")
    public void taskCommentAddedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        notificationService.notifyNewCommentAdded(task, notificationDto.getDontSendUserId());
    }

    @KafkaListener(topics = "task.comment.closed", groupId = "my-group")
    public void taskCommentClosedConsumer(String message) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Task task = taskService.getTask(notificationDto.getTaskId());

        if (!task.getIsParallel()) {
            notificationService.notifySupervisorTaskClosed(task, NotificationType.PARTIAL_EMPLOYEES_COMMENTED);

        } else
            notificationService.notifySupervisorTaskClosed(task, NotificationType.ALL_EMPLOYEES_COMMENTED);
    }
}
