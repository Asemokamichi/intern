package com.notification_manager.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_manager.dto.UserNotificationDto;
import com.notification_manager.service.UserNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserNotificationConsumer {
    private final UserNotificationService notificationService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user.created", groupId = "my-group")
    public void userCreatedConsumer(String message)  {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserCreation(userNotificationDto);
    }

    @KafkaListener(topics = "user.info.edited", groupId = "my-group")
    public void userInfoEditedConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserInfoUpdate(userNotificationDto);
    }


    @KafkaListener(topics = "user.position.update", groupId = "my-group")
    public void userPositionUpdateConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserPositionUpdate(userNotificationDto);
    }

    @KafkaListener(topics = "user.role.update", groupId = "my-group")
    public void userRoleUpdateConsumer(String message)  {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserRoleUpdate(userNotificationDto);
    }

    @KafkaListener(topics = "user.department.update", groupId = "my-group")
    public void userDepartmentUpdateConsumer(String message)  {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserDepartmentUpdate(userNotificationDto);
    }

    @KafkaListener(topics = "user.added.to.department", groupId = "my-group")
    public void userAddedToDepartmentConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserDepartmentUpdate(userNotificationDto);
    }

    @KafkaListener(topics = "user.activated", groupId = "my-group")
    public void userActivatedConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserActivation(userNotificationDto);
    }

    @KafkaListener(topics = "user.deactivated", groupId = "my-group")
    public void userDeactivatedConsumer(String message)  {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserDeactivation(userNotificationDto);
    }

    @KafkaListener(topics = "user.deleted", groupId = "my-group")
    public void userDeletedConsumer(String message)  {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyUserDeletion(userNotificationDto);
    }

    @KafkaListener(topics = "department.created", groupId = "my-group")
    public void departmentCreatedConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyDepartmentCreation(userNotificationDto);
    }

    @KafkaListener(topics = "department.update", groupId = "my-group")
    public void departmentUpdateConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyDepartmentUpdate(userNotificationDto);
    }

    @KafkaListener(topics = "department.delete", groupId = "my-group")
    public void departmentDeleteConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyDepartmentDelete(userNotificationDto);
    }

    @KafkaListener(topics = "department.head.delete", groupId = "my-group")
    public void departmentHeadDeleteConsumer(String message) {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyDepartmentHeadDelete(userNotificationDto);
    }

    @KafkaListener(topics = "department.parent.delete", groupId = "my-group")
    public void departmentParentDeleteConsumer(String message)  {
        UserNotificationDto userNotificationDto = readValue(message);
        notificationService.notifyDepartmentParentDelete(userNotificationDto);
    }

    private UserNotificationDto readValue(String message){
        try {
            return objectMapper.readValue(message, UserNotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
