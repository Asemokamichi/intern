package com.notification_manager.service.impl;


import com.notification_manager.entity.Notification;
import com.notification_manager.dto.NotificationDto;
import com.notification_manager.enums.NotificationType;
import com.notification_manager.repository.NotificationRepository;
import com.notification_manager.service.TaskNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class TaskNotificationServiceImpl implements TaskNotificationService {
    private final NotificationRepository notificationRepository;

    public TaskNotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // уведомление всех назначенных сотрудников о назначение новой задачи
    @Transactional
    public void notifyAssignmentOfNewTask(NotificationDto notificationDto) {
        notifyUser(notificationDto, notificationDto.getRecipientIds(), NotificationType.TASK_ASSIGNED);
    }

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    @Transactional
    public void notifyNewCommentAdded(NotificationDto notificationDto) {
        // уведомление руководителя
        notifyUser(notificationDto, NotificationType.TASK_COMMENT_ADDED);

        // уведомление всех остальных назначенных сотрудников
        for (Long responsible : notificationDto.getRecipientIds()) {
            if (responsible.equals(notificationDto.getDontRecipientId())) continue;

            notifyUser(notificationDto, responsible, NotificationType.TASK_COMMENT_ADDED);
        }
    }

    // уведомляем руководителя о том, что задача прокомментирована всеми назначенными сотрудниками
    public void notifySupervisorTaskClosed(NotificationDto notificationDto, NotificationType notificationType) {
        notifyUser(notificationDto, notificationType);
    }

    // уведомляем всех о завершении задачи
    public void notifyTaskCompleted(NotificationDto notificationDto) {
        notifyUser(notificationDto, notificationDto.getRecipientIds(), NotificationType.TASK_COMPLETED);
    }

    // уведомляем руководителя о принятии задачи и о старте работы
    public void notifyManagerAboutTaskAcceptance(NotificationDto notificationDto) {
        notifyUser(notificationDto, NotificationType.TASK_ACCEPTED_AND_STARTED);
    }

    // уведомляем всех о продлении дедлайна
    public void notifyAllAboutDeadlineExtension(NotificationDto notificationDto) {
        notifyUser(notificationDto, notificationDto.getRecipientIds(), NotificationType.TASK_DEADLINE_EXTENDED);
    }

    //уведомляем о получение решения задачи
    public void notifyResolutionReceived(NotificationDto notificationDto) {
        notifyUser(notificationDto, NotificationType.TASK_RESOLUTION_RECEIVED);
    }

    // уведомляем о том, что решение по задаче принято
    public void notifyTaskResolutionApproved(NotificationDto notificationDto) {
        notifyUser(notificationDto, notificationDto.getRecipientIds(), NotificationType.TASK_APPROVED);
        notifyTaskCompleted(notificationDto);
    }

    // уведомляем о том, что решение по задаче принято
    public void notifyTaskResolutionReturnedForRevision(NotificationDto notificationDto) {
        notifyUser(notificationDto, notificationDto.getRecipientIds(), NotificationType.TASK_RETURNED_FOR_REVISION);
    }

    // уведомляем об удалении задачи
    public void notifyTaskDeleted(NotificationDto notificationDto) {
        notifyUser(notificationDto, notificationDto.getRecipientIds(), NotificationType.TASK_DELETED);
    }

    // создает сущность Notification. После заполнение отправляет в бд
    private void notifyUser(NotificationDto notificationDto, Long userId, NotificationType notificationType) {
        Notification notification = Notification.builder()
                .userId(userId)
                .objectId(notificationDto.getTaskId())
                .creationDate(LocalDateTime.now())
                .message(notificationType.formatMessage(notificationDto.getTaskId(),
                        notificationDto.getTitle()))
                .build();

        notificationRepository.save(notification);
    }

    private void notifyUser(NotificationDto notificationDto, NotificationType notificationType) {
        notifyUser(notificationDto, notificationDto.getAuthorId(), notificationType);
    }

    private void notifyUser(NotificationDto notificationDto, Long[] recipientIds, NotificationType notificationType) {
        for (Long id : recipientIds) {
            notifyUser(notificationDto, id, notificationType);
        }
    }
}
