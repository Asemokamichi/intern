package com.user_manager.repository;

import com.user_manager.model.Department;
import com.user_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDepartmentId(Long departmentId);

    @Query("SELECT u.id FROM User u")
    List<Long> findAllUserIds();

    @Query("SELECT u.id FROM User u WHERE department =:department")
    List<Long> findAllUserIdsOfDepartment(Department department);




}
