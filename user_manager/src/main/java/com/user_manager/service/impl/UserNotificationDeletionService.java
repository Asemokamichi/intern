package com.user_manager.service.impl;

import com.user_manager.model.User;
import com.user_manager.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserNotificationDeletionService implements com.user_manager.service.UserNotificationDeletionService {
    private final NotificationRepository notificationRepository;


    @Transactional
    @Override
    public void deleteUserNotifications(User user) {
        Long id = user.getId();
        notificationRepository.deleteAllByUserId(id);
    }
}
