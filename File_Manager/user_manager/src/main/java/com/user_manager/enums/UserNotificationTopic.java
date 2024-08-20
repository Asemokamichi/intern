package com.user_manager.enums;

public enum UserNotificationTopic {
    USER_ROLE_UPDATE("user.role.update"),
    USER_POSITION_UPDATE("user.position.update"),
    USER_CREATED("user.created"),
    USER_INFO_EDITED("user.info.edited"),
    USER_DEPARTMENT_UPDATE("user.department.update"),
    USER_ACTIVATED("user.activated"),
    USER_DEACTIVATED("user.deactivated"),
    USER_DELETED("user.deleted"),
    DEPARTMENT_CREATED("department.created"),
    DEPARTMENT_UPDATE("department.update"),
    DEPARTMENT_DELETE("department.delete"),
    DEPARTMENT_HEAD_DELETE("department.head.delete"),
    DEPARTMENT_PARENT_DELETE("department.parent.delete"),
    USER_ADDED_TO_DEPARTMENT("user.added.to.department");

    private final String topicName;

    UserNotificationTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
