package com.notification_manager.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_manager.dto.TaskNotificationDto;
import com.notification_manager.enums.TaskNotificationType;
import com.notification_manager.service.TaskNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskNotificationConsumer {

    private final TaskNotificationService notificationService;

    private final ObjectMapper objectMapper;

    public TaskNotificationConsumer(TaskNotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "task.assigned", groupId = "my-group")
    public void taskAssignedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyAssignmentOfNewTask(taskNotificationDto);
    }

    @KafkaListener(topics = "task.deadline.extended", groupId = "my-group")
    public void taskDeadlineExtendedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyAllAboutDeadlineExtension(taskNotificationDto);
    }

    @KafkaListener(topics = "task.deleted", groupId = "my-group")
    public void taskDeletedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyTaskDeleted(taskNotificationDto);
    }

    @KafkaListener(topics = "task.completed", groupId = "my-group")
    public void taskCompletedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyTaskCompleted(taskNotificationDto);
    }

    @KafkaListener(topics = "task.accepted.started", groupId = "my-group")
    public void taskAcceptedStartedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyManagerAboutTaskAcceptance(taskNotificationDto);
    }

    @KafkaListener(topics = "task.resolution.received", groupId = "my-group")
    public void taskResolutionReceivedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyResolutionReceived(taskNotificationDto);
    }

    @KafkaListener(topics = "task.resolution.approved", groupId = "my-group")
    public void taskResolutionApprovedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyTaskResolutionApproved(taskNotificationDto);
    }

    @KafkaListener(topics = "task.resolution.revision", groupId = "my-group")
    public void taskResolutionRevisionConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyTaskResolutionReturnedForRevision(taskNotificationDto);
    }

    @KafkaListener(topics = "task.comment.added", groupId = "my-group")
    public void taskCommentAddedConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);

        notificationService.notifyNewCommentAdded(taskNotificationDto);
    }

    @KafkaListener(topics = "task.comment.closed.parallel", groupId = "my-group")
    public void taskCommentClosedParallelConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);
        notificationService.notifySupervisorTaskClosed(taskNotificationDto, TaskNotificationType.ALL_EMPLOYEES_COMMENTED);
    }

    @KafkaListener(topics = "task.comment.closed.nonparallel", groupId = "my-group")
    public void taskCommentClosedNonParallelConsumer(String message) {
        TaskNotificationDto taskNotificationDto = readValue(message);
        notificationService.notifySupervisorTaskClosed(taskNotificationDto, TaskNotificationType.PARTIAL_EMPLOYEES_COMMENTED);
    }




    private TaskNotificationDto readValue(String message){
        try {
            return objectMapper.readValue(message, TaskNotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
