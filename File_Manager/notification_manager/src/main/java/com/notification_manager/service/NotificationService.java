package com.notification_manager.service;

import com.notification_manager.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    List<Notification> getNotifications(Long id);

    //помечаем прочитанныем определенное уведомление
    void markNotificationAsRead(Long userId, Long noteId);

    //помечаем прочитанным все уведомление
    void markAllNotificationsAsRead(Long userId);

    void clearReadNotifications();
}
