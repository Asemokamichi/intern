package com.notification_manager.service;

import com.notification_manager.dto.NotificationDto;
import com.notification_manager.enums.NotificationType;
import org.springframework.stereotype.Service;

@Service
public interface TaskNotificationService {

    // уведомление всех назначенных сотрудников о назначение новой задачи
    void notifyAssignmentOfNewTask(NotificationDto notificationDto);

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    void notifyNewCommentAdded(NotificationDto notificationDto);

    // уведомляем руководителя о том, что задача прокомментирована всеми назначенными сотрудниками
    void notifySupervisorTaskClosed(NotificationDto notificationDto, NotificationType notificationType);

    // уведомляем всех о завершении задачи
    void notifyTaskCompleted(NotificationDto notificationDto);

    // уведомляем руководителя о принятии задачи и о старте работы
    void notifyManagerAboutTaskAcceptance(NotificationDto notificationDto);

    // уведомляем всех о продлении дедлайна
    void notifyAllAboutDeadlineExtension(NotificationDto notificationDto);

    //уведомляем о получение решения задачи
    void notifyResolutionReceived(NotificationDto notificationDto);

    // уведомляем о том, что решение по задаче принято
    void notifyTaskResolutionApproved(NotificationDto notificationDto);

    // уведомляем о том, что решение по задаче принято
    void notifyTaskResolutionReturnedForRevision(NotificationDto notificationDto);

    // уведомляем об удалении задачи
    void notifyTaskDeleted(NotificationDto notificationDto);
}