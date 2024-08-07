package com.task_manager.service;

import com.task_manager.entity.*;
import com.task_manager.enums.NotificationType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    List<Notification> getNotifications(Long id);

    void markNotificationAsRead(Long userId, Long noteId);

    void markAllNotificationsAsRead(Long userId);

    // уведомление всех назначенных сотрудников о назначение новой задачи
    void notifyAssignmentOfNewTask(List<Responsible> responsibles);

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    void notifyNewCommentAdded(Resolution resolution);

    // уведомляем руководителя о том, что задача прокомментирована всеми назначенными сотрудниками
    void notifySupervisorTaskClosed(Task task, NotificationType notificationType);

    // уведомляем всех о завершении задачи
    void notifyTaskCompleted(Task task);

    // уведомляем руководителя о принятии задачи и о старте работы
    void notifyManagerAboutTaskAcceptance(Task task);

    // уведомляем всех о продлении дедлайна
    void notifyAllAboutDeadlineExtension(Task task);

    //уведомляем о получение решения задачи
    void notifyResolutionReceived(Task task);

    // уведомляем о том, что решение по задаче принято
    void notifyTaskResolutionApproved(Task task);

    // уведомляем о том, что решение по задаче принято
    void notifyTaskResolutionReturnedForRevision(Task task);

    // уведомляем об удалении задачи
    void notifyTaskDeleted(Task task);
}