package com.notification_manager.service.impl;

import com.notification_manager.dto.UserNotificationDto;
import com.notification_manager.entity.Notification;
import com.notification_manager.enums.UserNotificationType;
import com.notification_manager.repository.NotificationRepository;
import com.notification_manager.service.UserNotificationService;
import java.time.LocalDateTime;


public class UserNotificationServiceImpl implements UserNotificationService {
    private final NotificationRepository notificationRepository;

    public UserNotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void notifyUserCreation(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_CREATED);
    }


    @Override
    public void notifyUserInfoUpdate(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_INFO_EDITED);
    }


    @Override
    public void notifyUserPositionUpdate(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_POSITION_UPDATE);
    }


    @Override
    public void notifyUserRoleUpdate(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_ROLE_UPDATE);
    }

    @Override
    public void notifyUserDepartmentUpdate(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_DEPARTMENT_UPDATE);
    }

    @Override
    public void notifyUserAddedToDepartment(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_ADDED_TO_DEPARTMENT);
    }

    @Override
    public void notifyUserActivation(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_ACTIVATED);
    }

    @Override
    public void notifyUserDeactivation(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_DEACTIVATED);
    }

    @Override
    public void notifyUserDeletion(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.USER_DELETED);
    }

    @Override
    public void notifyDepartmentCreation(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.DEPARTMENT_CREATED);
    }

    @Override
    public void notifyDepartmentUpdate(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.DEPARTMENT_UPDATE);
    }

    @Override
    public void notifyDepartmentDelete(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.DEPARTMENT_DELETE);
    }

    @Override
    public void notifyDepartmentHeadDelete(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.DEPARTMENT_HEAD_DELETE);
    }

    @Override
    public void notifyDepartmentParentDelete(UserNotificationDto userNotificationDto) {
        notifyAllUser(userNotificationDto, UserNotificationType.DEPARTMENT_PARENT_DELETE);
    }

        //create a notification
    private void notifyUser(Long recipientId, UserNotificationType notificationType, Long objectId){

        Notification notification = Notification.builder()
                        .recipientId(recipientId)
                        .objectId(objectId)
                        .creationDate(LocalDateTime.now())
                        .message(notificationType.formatMessage(objectId))
                        .build();
        notificationRepository.save(notification);
    }


    private void notifyAllUser(UserNotificationDto userNotificationDto, UserNotificationType notificationType) {
        for (Long id : userNotificationDto.getRecipientIds()){
            notifyUser(id, notificationType, userNotificationDto.getObjectId());
        }
    }


}
