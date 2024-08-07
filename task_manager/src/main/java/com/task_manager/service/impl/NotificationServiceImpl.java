package com.task_manager.service.impl;

import com.task_manager.entity.*;
import com.task_manager.enums.NotificationType;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.repository.NotificationRepository;
import com.task_manager.service.NotificationService;
import com.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    //получаем все уведомление
    @Override
    public List<Notification> getNotifications(Long id) {
        User user = userService.findById(id);

        List<Notification> notifications = notificationRepository.findByUserAndViewedFalse(user);

        return notifications;
    }

    //помечаем прочитанныем определенное уведомление
    @Override
    public void markNotificationAsRead(Long userId, Long noteId) {
        User user = userService.findById(userId);

        Notification notification = notificationRepository.findByUserAndIdAndViewedFalse(user, noteId)
                .orElseThrow(() -> new InvalidRequest("Уведомление не найдено..."));

        notification.setViewed(true);

        notificationRepository.save(notification);
    }

    //помечаем прочитанным все уведомление
    @Override
    public void markAllNotificationsAsRead(Long userId) {
        User user = userService.findById(userId);

        List<Notification> notifications = notificationRepository.findByUserAndViewedFalse(user);

        for (Notification notification : notifications) {
            notification.setViewed(true);

            notificationRepository.save(notification);
        }
    }

    // уведомление всех назначенных сотрудников о назначение новой задачи
    @Transactional
    public void notifyAssignmentOfNewTask(List<Responsible> responsibles) {
        notifyUser(responsibles, NotificationType.TASK_ASSIGNED);
    }

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    @Transactional
    public void notifyNewCommentAdded(Resolution resolution) {
        Task task = resolution.getTask();

        // уведомление руководителя
        notifyUser(task, NotificationType.TASK_COMMENT_ADDED);

        // уведомление всех остальных назначенных сотрудников
        for (Responsible responsible : task.getResponsibles()) {
            if (responsible.getUser() == resolution.getUser()) continue;

            notifyUser(task, responsible.getUser(), NotificationType.TASK_COMMENT_ADDED);
        }
    }

    // уведомляем руководителя о том, что задача прокомментирована всеми назначенными сотрудниками
    public void notifySupervisorTaskClosed(Task task, NotificationType notificationType) {
        notifyUser(task, notificationType);
    }

    // уведомляем всех о завершении задачи
    public void notifyTaskCompleted(Task task) {
        notifyUser(task.getResponsibles(), NotificationType.TASK_COMPLETED);
    }

    // уведомляем руководителя о принятии задачи и о старте работы
    public void notifyManagerAboutTaskAcceptance(Task task) {
        notifyUser(task, NotificationType.TASK_ACCEPTED_AND_STARTED);
    }

    // уведомляем всех о продлении дедлайна
    public void notifyAllAboutDeadlineExtension(Task task) {
        notifyUser(task.getResponsibles(), NotificationType.TASK_DEADLINE_EXTENDED);
    }

    //уведомляем о получение решения задачи
    public void notifyResolutionReceived(Task task) {
        notifyUser(task, NotificationType.TASK_RESOLUTION_RECEIVED);
    }

    // уведомляем о том, что решение по задаче принято
    public void notifyTaskResolutionApproved(Task task) {
        notifyUser(task.getResponsibles(), NotificationType.TASK_APPROVED);
        notifyTaskCompleted(task);
    }

    // уведомляем о том, что решение по задаче принято
    public void notifyTaskResolutionReturnedForRevision(Task task) {
        notifyUser(task.getResponsibles(), NotificationType.TASK_RETURNED_FOR_REVISION);
    }

    // уведомляем об удалении задачи
    public void notifyTaskDeleted(Task task) {
        notifyUser(task.getResponsibles(), NotificationType.TASK_DELETED);
    }

    // создает сущность Notification. После заполнение отправляет в бд
    private void notifyUser(Task task, User user, NotificationType notificationType) {
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTask(task);
        notification.setCreationDate(LocalDateTime.now());
        notification.setMessage(notificationType.formatMessage(task.getId()));

        notificationRepository.save(notification);
    }

    private void notifyUser(Task task, NotificationType notificationType) {
        notifyUser(task, task.getUser(), notificationType);
    }

    private void notifyUser(List<Responsible> responsibles, NotificationType notificationType) {
        for (Responsible responsible : responsibles) {
            notifyUser(responsible.getTask(), responsible.getUser(), notificationType);
        }
    }
}
