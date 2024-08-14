package com.user_manager.service.impl;

import com.user_manager.enums.NotificationType;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Department;
import com.user_manager.model.Notification;
import com.user_manager.model.User;
import com.user_manager.repository.NotificationRepository;
import com.user_manager.service.NotificationService;
import com.user_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Override
    public void notifyUserCreation(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_CREATED);
    }


    @Override
    public void notifyUserInfoUpdate(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_INFO_EDITED);
    }


    @Override
    public void notifyUserPositionUpdate(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_POSITION_UPDATE);
    }


    @Override
    public void notifyUserRoleUpdate(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_ROLE_UPDATE);
    }

    @Override
    public void notifyUserDepartmentUpdate(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_DEPARTMENT_UPDATE);
    }

    @Override
    public void notifyUserAddedToDepartment(Long userId) throws NotFoundException {
        notifyDepartmentUser(userId);
    }

    @Override
    public void notifyUserActivation(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_ACTIVATED);
    }

    @Override
    public void notifyUserDeactivation(Long userId) throws NotFoundException {
        notifyAllUser(userId, NotificationType.USER_DEACTIVATED);
    }

    @Override
    public void notifyUserDeletion(Long deletedUserId) throws NotFoundException {
        notifyAllUser(deletedUserId);
    }

    @Override
    public void notifyDepartmentCreation(Long departmentId) throws NotFoundException {
        notifyAllUser(departmentId, NotificationType.DEPARTMENT_CREATED);
    }

    @Override
    public void notifyDepartmentUpdate(Long departmentId) throws NotFoundException {
        notifyAllUser(departmentId, NotificationType.DEPARTMENT_UPDATE);
    }

    @Override
    public void notifyDepartmentDelete(Long departmentId) throws NotFoundException {
        notifyAllUser(departmentId, NotificationType.DEPARTMENT_DELETE);
    }

    @Override
    public void notifyDepartmentHeadDelete(Long departmentId) throws NotFoundException {
        notifyAllUser(departmentId, NotificationType.DEPARTMENT_HEAD_DELETE);
    }

    @Override
    public void notifyDepartmentParentDelete(Long departmentId) throws NotFoundException {
        notifyAllUser(departmentId, NotificationType.DEPARTMENT_PARENT_DELETE);
    }


    //create a notification
    private void notifyUser(User user, NotificationType notificationType, User newUser){
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setCreationDate(LocalDateTime.now());
        notification.setMessage(notificationType.formatMessage(newUser.getId()));

        notificationRepository.save(notification);
    }

    private void notifyUser(User user, NotificationType notificationType, Long newUserId){
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setCreationDate(LocalDateTime.now());
        notification.setMessage(notificationType.formatMessage(newUserId));

        notificationRepository.save(notification);
    }


    private void notifyAllUser(Long newUserId, NotificationType notificationType) throws NotFoundException {
        List<Long> allUserId = userService.getAllUserIds();
        for (Long id : allUserId){
            notifyUser(userService.getUserById(id), notificationType, newUserId);
        }
    }

    private void notifyDepartmentUser(Long newUserId) throws NotFoundException {
        User user = userService.getUserById(newUserId);
        Department department = user.getDepartment();
        List<Long> allDepartmentUserId = userService.findAllUserIdsOfDepartment(department);

        for (Long id : allDepartmentUserId){
            notifyUser(userService.getUserById(id), NotificationType.USER_ADDED_TO_DEPARTMENT, newUserId);
        }
    }

    private void notifyAllUser(Long deletedUserId) throws NotFoundException {
        List<Long> allUserId = userService.getAllUserIds();
        for (Long id : allUserId){
            notifyUser(userService.getUserById(id), NotificationType.USER_DELETED, deletedUserId);
        }
    }


}
