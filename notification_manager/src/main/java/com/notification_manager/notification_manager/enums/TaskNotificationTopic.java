package com.notification_manager.notification_manager.enums;

public enum TaskNotificationTopic {
    TASK_ASSIGNED("task.assigned"),
    TASK_COMMENT_ADDED("task.comment.added"),
    TASK_COMMENT_CLOSED("task.comment.closed"),
    TASK_COMPLETED("task.completed"),
    TASK_ACCEPTED_STARTED("task.accepted.started"),
    TASK_DEADLINE_EXTENDED("task.deadline.extended"),
    TASK_RESOLUTION_RECEIVED("task.resolution.received"),
    TASK_RESOLUTION_APPROVED("task.resolution.approved"),
    TASK_RESOLUTION_REVISION("task.resolution.revision"),
    TASK_DELETED("task.deleted");

    private final String topicName;

    TaskNotificationTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
