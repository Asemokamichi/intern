package com.notification_manager.service.impl;


import com.notification_manager.entity.Notification;
import com.notification_manager.dto.TaskNotificationDto;
import com.notification_manager.enums.TaskNotificationType;
import com.notification_manager.repository.NotificationRepository;
import com.notification_manager.service.TaskNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TaskNotificationServiceImpl implements TaskNotificationService {
    private final NotificationRepository notificationRepository;

    public TaskNotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // уведомление всех назначенных сотрудников о назначение новой задачи
    @Transactional
    public void notifyAssignmentOfNewTask(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, taskNotificationDto.getRecipientIds(), TaskNotificationType.TASK_ASSIGNED);
    }

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    @Transactional
    public void notifyNewCommentAdded(TaskNotificationDto taskNotificationDto) {
        // уведомление руководителя
        notifyUser(taskNotificationDto, TaskNotificationType.TASK_COMMENT_ADDED);

        // уведомление всех остальных назначенных сотрудников
        for (Long responsible : taskNotificationDto.getRecipientIds()) {
            if (responsible.equals(taskNotificationDto.getDontRecipientId())) continue;

            notifyUser(taskNotificationDto, responsible, TaskNotificationType.TASK_COMMENT_ADDED);
        }
    }

    // уведомляем руководителя о том, что задача прокомментирована всеми назначенными сотрудниками
    public void notifySupervisorTaskClosed(TaskNotificationDto taskNotificationDto, TaskNotificationType taskNotificationType) {
        notifyUser(taskNotificationDto, taskNotificationType);
    }

    // уведомляем всех о завершении задачи
    public void notifyTaskCompleted(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, taskNotificationDto.getRecipientIds(), TaskNotificationType.TASK_COMPLETED);
    }

    // уведомляем руководителя о принятии задачи и о старте работы
    public void notifyManagerAboutTaskAcceptance(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, TaskNotificationType.TASK_ACCEPTED_AND_STARTED);
    }

    // уведомляем всех о продлении дедлайна
    public void notifyAllAboutDeadlineExtension(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, taskNotificationDto.getRecipientIds(), TaskNotificationType.TASK_DEADLINE_EXTENDED);
    }

    //уведомляем о получение решения задачи
    public void notifyResolutionReceived(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, TaskNotificationType.TASK_RESOLUTION_RECEIVED);
    }

    // уведомляем о том, что решение по задаче принято
    public void notifyTaskResolutionApproved(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, taskNotificationDto.getRecipientIds(), TaskNotificationType.TASK_APPROVED);
        notifyTaskCompleted(taskNotificationDto);
    }

    // уведомляем о том, что решение по задаче принято
    public void notifyTaskResolutionReturnedForRevision(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, taskNotificationDto.getRecipientIds(), TaskNotificationType.TASK_RETURNED_FOR_REVISION);
    }

    // уведомляем об удалении задачи
    public void notifyTaskDeleted(TaskNotificationDto taskNotificationDto) {
        notifyUser(taskNotificationDto, taskNotificationDto.getRecipientIds(), TaskNotificationType.TASK_DELETED);
    }

    // создает сущность Notification. После заполнение отправляет в бд
    private void notifyUser(TaskNotificationDto taskNotificationDto, Long userId, TaskNotificationType taskNotificationType) {
        Notification notification = Notification.builder()
                .recipientId(userId)
                .objectId(taskNotificationDto.getTaskId())
                .creationDate(LocalDateTime.now())
                .message(taskNotificationType.formatMessage(taskNotificationDto.getTaskId(),
                        taskNotificationDto.getTitle()))
                .build();

        notificationRepository.save(notification);
    }

    private void notifyUser(TaskNotificationDto taskNotificationDto, TaskNotificationType taskNotificationType) {
        notifyUser(taskNotificationDto, taskNotificationDto.getAuthorId(), taskNotificationType);
    }

    private void notifyUser(TaskNotificationDto taskNotificationDto, Long[] recipientIds, TaskNotificationType taskNotificationType) {
        for (Long id : recipientIds) {
            notifyUser(taskNotificationDto, id, taskNotificationType);
        }
    }
}
