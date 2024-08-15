package com.notification_manager.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_manager.dto.NotificationDto;
import com.notification_manager.enums.NotificationType;
import com.notification_manager.service.TaskNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final TaskNotificationService taskNotificationService;

    private final ObjectMapper objectMapper;

    public NotificationConsumer(TaskNotificationService taskNotificationService, ObjectMapper objectMapper) {
        this.taskNotificationService = taskNotificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "task.assigned", groupId = "my-group")
    public void taskAssignedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyAssignmentOfNewTask(notificationDto);
    }

    @KafkaListener(topics = "task.deadline.extended", groupId = "my-group")
    public void taskDeadlineExtendedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyAllAboutDeadlineExtension(notificationDto);
    }

    @KafkaListener(topics = "task.deleted", groupId = "my-group")
    public void taskDeletedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyTaskDeleted(notificationDto);
    }

    @KafkaListener(topics = "task.completed", groupId = "my-group")
    public void taskCompletedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyTaskCompleted(notificationDto);
    }

    @KafkaListener(topics = "task.accepted.started", groupId = "my-group")
    public void taskAcceptedStartedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyManagerAboutTaskAcceptance(notificationDto);
    }

    @KafkaListener(topics = "task.resolution.received", groupId = "my-group")
    public void taskResolutionReceivedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyResolutionReceived(notificationDto);
    }

    @KafkaListener(topics = "task.resolution.approved", groupId = "my-group")
    public void taskResolutionApprovedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyTaskResolutionApproved(notificationDto);
    }

    @KafkaListener(topics = "task.resolution.revision", groupId = "my-group")
    public void taskResolutionRevisionConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyTaskResolutionReturnedForRevision(notificationDto);
    }

    @KafkaListener(topics = "task.comment.added", groupId = "my-group")
    public void taskCommentAddedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        taskNotificationService.notifyNewCommentAdded(notificationDto);
    }

    @KafkaListener(topics = "task.comment.closed.parallel", groupId = "my-group")
    public void taskCommentClosedParallelConsumer(String message) {
        NotificationDto notificationDto = readValue(message);
        taskNotificationService.notifySupervisorTaskClosed(notificationDto, NotificationType.ALL_EMPLOYEES_COMMENTED);
    }

    @KafkaListener(topics = "task.comment.closed.nonparallel", groupId = "my-group")
    public void taskCommentClosedNonParallelConsumer(String message) {
        NotificationDto notificationDto = readValue(message);
        taskNotificationService.notifySupervisorTaskClosed(notificationDto, NotificationType.PARTIAL_EMPLOYEES_COMMENTED);
    }


    private NotificationDto readValue(String message){
        try {
            return objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
