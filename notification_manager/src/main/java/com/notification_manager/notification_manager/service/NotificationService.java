package com.notification_manager.notification_manager.service;

import com.notification_manager.notification_manager.dto.NotificationDto;
import com.notification_manager.notification_manager.enums.NotificationType;
import com.notification_manager.notification_manager.entity.Notification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface NotificationService {
    List<Notification> getNotifications(Long id);

    //помечаем прочитанныем определенное уведомление
    void markNotificationAsRead(Long userId, Long noteId);

    //помечаем прочитанным все уведомление
    void markAllNotificationsAsRead(Long userId);

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

    void clearReadNotifications();
}