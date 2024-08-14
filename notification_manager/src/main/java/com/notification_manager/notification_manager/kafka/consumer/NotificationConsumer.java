package com.notification_manager.notification_manager.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_manager.notification_manager.dto.NotificationDto;
import com.notification_manager.notification_manager.enums.NotificationType;
import com.notification_manager.notification_manager.service.NotificationService;
import com.notification_manager.notification_manager.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics = "task.assigned", groupId = "my-group")
    public void taskAssignedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyAssignmentOfNewTask(notificationDto);
    }

    @KafkaListener(topics = "task.deadline.extended", groupId = "my-group")
    public void taskDeadlineExtendedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyAllAboutDeadlineExtension(notificationDto);
    }

    @KafkaListener(topics = "task.deleted", groupId = "my-group")
    public void taskDeletedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyTaskDeleted(notificationDto);
    }

    @KafkaListener(topics = "task.completed", groupId = "my-group")
    public void taskCompletedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyTaskCompleted(notificationDto);
    }

    @KafkaListener(topics = "task.accepted.started", groupId = "my-group")
    public void taskAcceptedStartedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyManagerAboutTaskAcceptance(notificationDto);
    }

    @KafkaListener(topics = "task.resolution.received", groupId = "my-group")
    public void taskResolutionReceivedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyResolutionReceived(notificationDto);
    }

    @KafkaListener(topics = "task.resolution.approved", groupId = "my-group")
    public void taskResolutionApprovedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyTaskResolutionApproved(notificationDto);
    }

    @KafkaListener(topics = "task.resolution.revision", groupId = "my-group")
    public void taskResolutionRevisionConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyTaskResolutionReturnedForRevision(notificationDto);
    }

    @KafkaListener(topics = "task.comment.added", groupId = "my-group")
    public void taskCommentAddedConsumer(String message) {
        NotificationDto notificationDto = readValue(message);

        notificationService.notifyNewCommentAdded(notificationDto);
    }

    @KafkaListener(topics = "task.comment.closed.parallel", groupId = "my-group")
    public void taskCommentClosedParallelConsumer(String message) {
        NotificationDto notificationDto = readValue(message);
        notificationService.notifySupervisorTaskClosed(notificationDto, NotificationType.ALL_EMPLOYEES_COMMENTED);
    }

    @KafkaListener(topics = "task.comment.closed.nonparallel", groupId = "my-group")
    public void taskCommentClosedNonParallelConsumer(String message) {
        NotificationDto notificationDto = readValue(message);
        notificationService.notifySupervisorTaskClosed(notificationDto, NotificationType.PARTIAL_EMPLOYEES_COMMENTED);
    }


    private NotificationDto readValue(String message){
        try {
            return objectMapper.readValue(message, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
