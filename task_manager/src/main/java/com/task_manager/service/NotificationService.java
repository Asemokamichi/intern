package com.task_manager.service;

import com.task_manager.entity.*;
import com.task_manager.enums.NotificationType;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface NotificationService {

    //получаем все уведомление
    List<Notification> getNotifications(Long id);

    //помечаем прочитанныем определенное уведомление
    void markNotificationAsRead(Long userId, Long noteId);

    //помечаем прочитанным все уведомление
    void markAllNotificationsAsRead(Long userId);

    // уведомление всех назначенных сотрудников о назначение новой задачи
    void notifyAssignmentOfNewTask(Task task);

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    void notifyNewCommentAdded(Task task, Long authorId);

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