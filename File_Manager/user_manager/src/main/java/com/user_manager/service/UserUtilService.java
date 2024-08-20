package com.user_manager.service;

import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Department;

import java.util.List;

public interface UserUtilService {
    Boolean exists(Long id) throws NotFoundException;
    List<Long> getAllUserIds();
    List<Long> findAllUserIdsOfDepartment(Department department);
}
