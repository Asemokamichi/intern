package com.notification_manager.service;

import com.notification_manager.dto.UserNotificationDto;
import org.springframework.stereotype.Service;

@Service
public interface UserNotificationService {

    //notify all user about new user
    void notifyUserCreation(UserNotificationDto userNotificationDto);

    //notify all user about user info update
    void notifyUserInfoUpdate(UserNotificationDto userNotificationDto);

    //notify all user about  user position update
    void notifyUserPositionUpdate(UserNotificationDto userNotificationDto);

    //notify all user about user role update
    void notifyUserRoleUpdate(UserNotificationDto userNotificationDto);

    //notify all user about user department update
    void notifyUserDepartmentUpdate(UserNotificationDto userNotificationDto);
    void notifyUserAddedToDepartment(UserNotificationDto userNotificationDto);

    //notify all user about user activation
    void  notifyUserActivation(UserNotificationDto userNotificationDto);

    //notify all user about user deactivation
    void  notifyUserDeactivation(UserNotificationDto userNotificationDto);

    //notify all user about user deletion
    void  notifyUserDeletion(UserNotificationDto userNotificationDto);

    //notify all user about new department
    void  notifyDepartmentCreation(UserNotificationDto userNotificationDto);

    //notify all user about  department update
    void  notifyDepartmentUpdate(UserNotificationDto userNotificationDto);

    //notify all user about new department
    void  notifyDepartmentDelete(UserNotificationDto userNotificationDto);

    //notify all user about new department
    void  notifyDepartmentHeadDelete(UserNotificationDto userNotificationDto);

    //notify all user about new department
    void  notifyDepartmentParentDelete(UserNotificationDto userNotificationDto);

}
