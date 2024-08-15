package com.notification_manager.service.impl;

import com.notification_manager.entity.Notification;
import com.notification_manager.repository.NotificationRepository;
import com.notification_manager.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    private final int day = 30;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    //получаем все уведомление
    @Override
    public List<Notification> getNotifications(Long id) {
        List<Notification> notifications = notificationRepository.findByRecipientIdAndViewedFalse(id);

        return notifications;
    }

    //помечаем прочитанныем определенное уведомление, ОБЪЯЗАТЕЛЬНО НАДО ПРОВЕРИТЬ ДАННЫЙ ФУНКЦИОНАЛ
    @Override
    public void markNotificationAsRead(Long userId, Long noteId) {
        notificationRepository.updateByIdAndUserId(userId, noteId);
    }

    //помечаем прочитанным все уведомление, ОБЪЯЗАТЕЛЬНО НАДО ПРОВЕРИТЬ ДАННЫЙ ФУНКЦИОНАЛ
    @Override
    public void markAllNotificationsAsRead(Long userId) {
        notificationRepository.updateByUserId(userId);
    }

    @Transactional
    @Override
    public void clearReadNotifications() {
        System.out.printf("[%s]: deletion started...\n", LocalDateTime.now());
        notificationRepository.deleteAllByViewedIsTrueOrCreationDateBefore(LocalDateTime.now().minusDays(day));
        System.out.printf("[%s]: deletion completed...\n", LocalDateTime.now());
    }

}
