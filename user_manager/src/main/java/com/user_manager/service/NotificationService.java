package com.user_manager.service;

import com.user_manager.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    //notify all user about new user
    void notifyUserCreation(Long userId) throws NotFoundException;

    //notify all user about user info update
    void notifyUserInfoUpdate(Long userId) throws NotFoundException;

    //notify all user about  user position update
    void notifyUserPositionUpdate(Long userId) throws NotFoundException;

    //notify all user about user role update
    void notifyUserRoleUpdate(Long userId) throws NotFoundException;

    //notify all user about user department update
    void notifyUserDepartmentUpdate(Long userId) throws NotFoundException;
    void notifyUserAddedToDepartment(Long userId) throws NotFoundException;

    //notify all user about user activation
    void  notifyUserActivation(Long userId) throws NotFoundException;

    //notify all user about user deactivation
    void  notifyUserDeactivation(Long userId) throws NotFoundException;

    //notify all user about user deletion
    void  notifyUserDeletion(Long deletedUserId) throws NotFoundException;

    //notify all user about new department
    void  notifyDepartmentCreation(Long departmentId) throws NotFoundException;

    //notify all user about  department update
    void  notifyDepartmentUpdate(Long departmentId) throws NotFoundException;

    //notify all user about new department
    void  notifyDepartmentDelete(Long departmentId) throws NotFoundException;

    //notify all user about new department
    void  notifyDepartmentHeadDelete(Long departmentId) throws NotFoundException;

    //notify all user about new department
    void  notifyDepartmentParentDelete(Long departmentId) throws NotFoundException;




}
