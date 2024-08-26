package com.file_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.file_manager.dto.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
