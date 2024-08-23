package com.file_manager.File_Manager.repositories;

import com.file_manager.File_Manager.dto.FileGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileGroupRepository extends JpaRepository<FileGroups, String> {
}
