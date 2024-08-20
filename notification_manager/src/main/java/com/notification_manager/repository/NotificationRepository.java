package com.notification_manager.repository;

import com.notification_manager.entity.Notification;
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
    List<Notification> findByRecipientIdAndViewedFalse(Long userId);

    Optional<Notification> findByRecipientIdAndIdAndViewedFalse(Long userId, Long id);

    @Modifying
    void deleteAllByViewedIsTrueOrCreationDateBefore(LocalDateTime date);

    @Modifying
    @Query("UPDATE Notification n SET n.viewed = true WHERE n.recipientId = :userId AND n.viewed = false")
    void updateByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Notification n SET n.viewed = true WHERE n.recipientId = :userId AND n.id = :noteId")
    void updateByIdAndUserId(@Param("userId") Long userId, @Param("noteId") Long noteId);

}
