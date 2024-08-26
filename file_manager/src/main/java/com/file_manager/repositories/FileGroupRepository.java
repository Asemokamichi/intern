package com.file_manager.repositories;

import com.file_manager.dto.FileGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileGroupRepository extends JpaRepository<FileGroups, String> {
}
