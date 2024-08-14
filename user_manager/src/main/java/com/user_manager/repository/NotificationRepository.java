package com.user_manager.repository;

import com.user_manager.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {
    void deleteAllByUserId(Long id);
}
