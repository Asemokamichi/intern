package com.user_manager.service;

import com.user_manager.dto.UserCreationRequest;
import com.user_manager.dto.UserInfoDto;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Role;
import com.user_manager.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserCreationRequest request) throws NotFoundException;
    User editUserInfo(Long id, UserInfoDto request) throws NotFoundException;
    String updateUserPosition(Long id, String position) throws NotFoundException;
    String updateUserRole(Long id, Role role) throws NotFoundException;
    String updateUserDepartment(Long id, Long departmentId) throws NotFoundException;
    String activateUser(Long id) throws NotFoundException;
    String deactivateUser(Long id) throws NotFoundException;
    String delete(Long id) throws NotFoundException;
    User getInfo(Long id);
    List<User> getInfo();
    Boolean existsById(Long id);
    User getUserById(Long id) throws NotFoundException;



}
