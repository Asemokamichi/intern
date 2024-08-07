package com.task_manager.enums;

public enum NotificationType {
    TASK_ASSIGNED("Задача №%d назначена"),
    TASK_DEADLINE_EXTENDED("Дедлайн задачи №%d продлен"),
    TASK_COMMENT_ADDED("К задаче №%d добавлен комментарий"),
    TASK_COMPLETED("Задача №%d завершена"),
    TASK_APPROVED("Решение по задаче №%d принято"),
    TASK_DELETED("Задача №%d удалена"),
    TASK_RETURNED_FOR_REVISION("Решение по задаче №%d отправлено на доработку"),
    ALL_EMPLOYEES_COMMENTED("Все назначенные сотрудники прокомментировали задачу №%d. Задача закрыта."),
    PARTIAL_EMPLOYEES_COMMENTED("Задача №%d закрыта, так как один из назначенных сотрудников прокомментировал её. Остальные комментарии не требуются."),
    TASK_ACCEPTED_AND_STARTED("Задача №%d принята и начата"),
    TASK_RESOLUTION_RECEIVED("Получено решение задачи №%d");
    private final String messageTemplate;

    NotificationType(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String formatMessage(long taskId) {
        return String.format(messageTemplate, taskId);
    }
}
