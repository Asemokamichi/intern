package com.task_manager.repository;

import com.task_manager.entity.Notification;
import com.task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndViewedFalse(User user);

    Optional<Notification> findByUserAndIdAndViewedFalse(User user, Long id);
}
