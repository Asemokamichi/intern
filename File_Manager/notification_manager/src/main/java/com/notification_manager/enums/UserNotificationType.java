package com.notification_manager.enums;

public enum UserNotificationType {
    USER_POSITION_UPDATE("User #%d position is updated"),
    USER_CREATED("New user with ID %d is created"),
    USER_INFO_EDITED("User #%d updated personal information"),
    USER_ROLE_UPDATE("User #%d role is updated"),
    USER_DEPARTMENT_UPDATE("User #%d department is updated"),
    USER_ACTIVATED("User #%d is activated"),
    USER_DEACTIVATED("User #%d is deactivated"),
    USER_DELETED("User #%d is deleted"),
    USER_ADDED_TO_DEPARTMENT("User #%d added to department"),
    DEPARTMENT_CREATED("Department with ID %d is created"),
    DEPARTMENT_UPDATE("Department #%d is updated"),
    DEPARTMENT_DELETE("Department #%d is deleted"),
    DEPARTMENT_HEAD_DELETE("Department #%d head is deleted"),
    DEPARTMENT_PARENT_DELETE("Department #%d parent is deleted");
    private final String messageTemplate;

    UserNotificationType(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String formatMessage(long userId) {
        return String.format(messageTemplate, userId);
    }

}
