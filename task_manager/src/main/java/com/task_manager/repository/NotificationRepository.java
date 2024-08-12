package com.task_manager.repository;

import com.task_manager.entity.Notification;
import com.task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndViewedFalse(User user);

    Optional<Notification> findByUserAndIdAndViewedFalse(User user, Long id);

    @Modifying
    void deleteAllByViewedIsTrueOrCreationDateBefore(LocalDateTime date);
}
