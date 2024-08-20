package com.notification_manager.enums;

public enum TaskNotificationType {
    TASK_ASSIGNED("Задача №%d '%s' назначена"),
    TASK_DEADLINE_EXTENDED("Дедлайн задачи №%d '%s' продлен"),
    TASK_COMMENT_ADDED("К задаче №%d '%s' добавлен комментарий"),
    TASK_COMPLETED("Задача №%d '%s' завершена"),
    TASK_APPROVED("Решение по задаче №%d '%s' принято"),
    TASK_DELETED("Задача №%d '%s' удалена"),
    TASK_RETURNED_FOR_REVISION("Решение по задаче №%d '%s' отправлено на доработку"),
    ALL_EMPLOYEES_COMMENTED("Все назначенные сотрудники прокомментировали задачу №%d '%s'. Задача закрыта."),
    PARTIAL_EMPLOYEES_COMMENTED("Задача №%d '%s' закрыта, так как один из назначенных сотрудников прокомментировал её. Остальные комментарии не требуются."),
    TASK_ACCEPTED_AND_STARTED("Задача №%d '%s' принята и начата"),
    TASK_RESOLUTION_RECEIVED("Получено решение задачи №%d '%s'");



    private final String messageTemplate;

    TaskNotificationType(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }


    public String formatMessage(long taskId, String taskTitle) {
        return String.format(messageTemplate, taskId, taskTitle);
    }
}
