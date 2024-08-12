package com.task_manager.repository;

import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
    boolean existsByUserAndTask(User user, Task task);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Resolution r WHERE r.user = :user AND r.task = :task AND (r.status = com.task_manager.enums.ResolutionStatus.SUBMITTED OR r.status = com.task_manager.enums.ResolutionStatus.APPROVED)")
    boolean existsByUserAndTaskWithStatusSubmittedOrApproved(@Param("user") User user, @Param("task") Task task);

    List<Resolution> findByTask(Task task);


    Optional<Resolution> findFirstByTaskOrderByIdDesc(Task task);
}
