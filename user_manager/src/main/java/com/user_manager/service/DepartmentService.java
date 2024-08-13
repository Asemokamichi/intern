package com.user_manager.service;

import com.user_manager.dto.DepartmentRequest;
import com.user_manager.dto.SingleDepartmentDto;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Department;

public interface DepartmentService {
    Department getDepartmentById(Long id) throws NotFoundException;
    Department createDepartment(DepartmentRequest request) throws NotFoundException;
    Department updateDepartment(Long id, DepartmentRequest request) throws NotFoundException;
    String deleteDepartmentHead(Long id) throws NotFoundException;
    String deleteParentDepartment(Long id) throws NotFoundException;
    String delete(Long id) throws NotFoundException;
    SingleDepartmentDto getDepartment(Long id) throws NotFoundException;
    Boolean exists(Long id) throws NotFoundException;
    String buildDepartmentTree(Long departmentId) throws NotFoundException;

}
