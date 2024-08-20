package com.notification_manager.service;

import com.notification_manager.dto.TaskNotificationDto;
import com.notification_manager.enums.TaskNotificationType;
import org.springframework.stereotype.Service;

@Service
public interface TaskNotificationService {

    // уведомление всех назначенных сотрудников о назначение новой задачи
    void notifyAssignmentOfNewTask(TaskNotificationDto taskNotificationDto);

    // уведомление руководителя и всех остальных назначенных сотрудников о добавления комментария
    void notifyNewCommentAdded(TaskNotificationDto taskNotificationDto);

    // уведомляем руководителя о том, что задача прокомментирована всеми назначенными сотрудниками
    void notifySupervisorTaskClosed(TaskNotificationDto taskNotificationDto, TaskNotificationType taskNotificationType);

    // уведомляем всех о завершении задачи
    void notifyTaskCompleted(TaskNotificationDto taskNotificationDto);

    // уведомляем руководителя о принятии задачи и о старте работы
    void notifyManagerAboutTaskAcceptance(TaskNotificationDto taskNotificationDto);

    // уведомляем всех о продлении дедлайна
    void notifyAllAboutDeadlineExtension(TaskNotificationDto taskNotificationDto);

    //уведомляем о получение решения задачи
    void notifyResolutionReceived(TaskNotificationDto taskNotificationDto);

    // уведомляем о том, что решение по задаче принято
    void notifyTaskResolutionApproved(TaskNotificationDto taskNotificationDto);

    // уведомляем о том, что решение по задаче принято
    void notifyTaskResolutionReturnedForRevision(TaskNotificationDto taskNotificationDto);

    // уведомляем об удалении задачи
    void notifyTaskDeleted(TaskNotificationDto taskNotificationDto);
}