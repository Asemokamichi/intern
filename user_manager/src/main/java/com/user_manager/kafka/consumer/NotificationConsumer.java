package com.user_manager.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_manager.exception.NotFoundException;
import com.user_manager.service.NotificationService;
import com.user_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final UserService userService;

    @KafkaListener(topics = "user.created", groupId = "users")
    public void userCreatedConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserCreation(id);
    }

    @KafkaListener(topics = "user.info.edited", groupId = "users")
    public void userInfoEditedConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserInfoUpdate(id);
    }


    @KafkaListener(topics = "user.position.update", groupId = "users")
    public void userPositionUpdateConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserPositionUpdate(id);
    }

    @KafkaListener(topics = "user.role.update", groupId = "users")
    public void userRoleUpdateConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserRoleUpdate(id);
    }

    @KafkaListener(topics = "user.department.update", groupId = "users")
    public void userDepartmentUpdateConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserDepartmentUpdate(id);
    }

    @KafkaListener(topics = "user.added.to.department", groupId = "users")
    public void userAddedToDepartmentConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserAddedToDepartment(id);
    }

    @KafkaListener(topics = "user.activated", groupId = "users")
    public void userActivatedConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserActivation(id);
    }

    @KafkaListener(topics = "user.deactivated", groupId = "users")
    public void userDeactivatedConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserDeactivation(id);
    }

    @KafkaListener(topics = "user.deleted", groupId = "users")
    public void userDeletedConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyUserDeletion(id);
    }

    @KafkaListener(topics = "department.created", groupId = "users")
    public void departmentCreatedConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyDepartmentCreation(id);
    }

    @KafkaListener(topics = "department.update", groupId = "users")
    public void departmentUpdateConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyDepartmentUpdate(id);
    }

    @KafkaListener(topics = "department.delete", groupId = "users")
    public void departmentDeleteConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyDepartmentDelete(id);
    }

    @KafkaListener(topics = "department.head.delete", groupId = "users")
    public void departmentHeadDeleteConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyDepartmentHeadDelete(id);
    }

    @KafkaListener(topics = "department.parent.delete", groupId = "users")
    public void departmentParentDeleteConsumer(String message) throws NotFoundException {
        Long id = null;
        try {
            id = objectMapper.readValue(message, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        notificationService.notifyDepartmentParentDelete(id);
    }

}
