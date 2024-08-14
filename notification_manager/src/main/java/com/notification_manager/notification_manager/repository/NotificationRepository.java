package com.notification_manager.notification_manager.repository;

import com.notification_manager.notification_manager.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndViewedFalse(Long userId);

    Optional<Notification> findByUserIdAndIdAndViewedFalse(Long userId, Long id);

    @Modifying
    void deleteAllByViewedIsTrueOrCreationDateBefore(LocalDateTime date);
}
