package com.task_manager.service;

import com.task_manager.dto.AddResolutionDto;
import com.task_manager.dto.CreateTaskDto;
import com.task_manager.dto.DateExtensionDto;
import com.task_manager.dto.StatusDto;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TaskService {

    /*
     * Создание задачи
     * 1. проверяем данные на корректность
     * 2. получаем юзера по id в бд
     * 3. создаем новый task и закидываем все данные
     * 4. указываем статус CREATED
     * 5. если задача является NOTIFICATION, то она сразу закрывается.
     * 6. отправляем новый task в бд
     * 7. получаем назначенных юзеров и прикрепляем созданную задачу к ним и отправляем в бд
     * 8. отправляем уведомление о назначения нового задания
     */
    Task createTask(CreateTaskDto createTaskDto);

    // Получение списка всех задач
    List<Task> getAllTasks();

    // Получение задачи по ID
    Task getTask(Long id);

    // продление дедлайна
    Task dateExtension(Long id, DateExtensionDto dateExtensionDto);

    // Удаление задачи
    void deleteTask(Long id);

    // Завершение задачи
    // 1. изменяем статус задачи на COMPLETED
    // 2. уведомляем всех о завершении задачи
    void completedTask(Long id);

    void completedTask(Task task);

    // 1. получение задачи
    // 2. намечаем статус задачи, как IN_PROGRESS
    // 3. отправляем уведомление руководителю о начале решения задачи
    void startTaskExecution(Long id, Long userId);

    // 1. отправляем решение на проверку
    // 2. намечаем статус решение, как SUBMITTED
    // 3. отправляем уведомление руководителю о получение решения задачи
    public void submitTaskResolution(Long id, AddResolutionDto addResolutionDto);


    public void taskStatus(Long id, StatusDto statusDto);

    List<Task> getActiveTasksByAuthor(Long id);

//    Получение списка активных задач, созданных пользователем
    List<Task> getActiveTasksByAssignee(Long id);

//    Добавление коммента к задаче
//    1. Если сотрудник попытается прокомментировать уже закрытую задачу, то вызывается исключение, сообщающая, что это невозможно
//    2. Если значение указанное в 'isParallel' является 'false', то задача закрывается сразу после того, как прокомментирует один из сотрудников
    void addCommentToTask(Long id, AddResolutionDto commentDto);

//    Получение комментариев к задаче
    List<Resolution> getCommentsToTask(Long id);


}
