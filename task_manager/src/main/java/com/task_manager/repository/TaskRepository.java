package com.task_manager.repository;

import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserAndFinishDateIsNull(User user);
}
