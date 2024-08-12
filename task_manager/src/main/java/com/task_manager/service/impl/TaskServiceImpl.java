package com.task_manager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task_manager.dto.*;
import com.task_manager.entity.Notification;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import com.task_manager.enums.NotificationType;
import com.task_manager.enums.Status;
import com.task_manager.enums.TaskNotificationTopic;
import com.task_manager.enums.TypeTask;
import com.task_manager.exceptions.AlreadyExists;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.exceptions.ResourceNotFound;
import com.task_manager.kafka.producer.NotificationProducer;
import com.task_manager.repository.TaskRepository;
import com.task_manager.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    private final ResolutionService resolutionService;
    private final NotificationProducer notificationProducer;
    private final NotificationService notificationService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, NotificationService notificationService, ResolutionService resolutionService, NotificationProducer notificationProducer) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.resolutionService = resolutionService;
        this.notificationProducer = notificationProducer;
    }

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
    @Transactional
    public Task createTask(CreateTaskDto createTaskDto) {
        if (!createTaskDto.checkValidation()) {
            throw new InvalidRequest("Предоставленные данные неполные, повторите операцию.");
        }

        User user = userService.findById(createTaskDto.getAuthorID());
        if (!userService.findExistingUserIds(createTaskDto.getResponsibles())){
            throw new InvalidRequest("Некоторые из указанных сотрудников не найдены. Пожалуйста, проверьте и повторите запрос.");
        }

        if (createTaskDto.getType().equals("ASSIGNMENT") && createTaskDto.getResponsibles().length > 1) {
            throw new InvalidRequest("Для задач типа ASSIGNMENT можно назначать только одного сотрудника. Измените тип задачи или разделите задачу для каждого сотрудника.");
        }

        Task task = new Task(createTaskDto);
        task.setUser(user);
        task.setStatus(Status.CREATED);

        if (task.getTypeTask() == TypeTask.NOTIFICATION) {
            task.setFinishDate(LocalDateTime.now());
            task.setStatus(Status.COMPLETED);
        }

        taskRepository.save(task);

        NotificationDto notificationDto = new NotificationDto(task);
        notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_ASSIGNED);

        return task;
    }

    // Получение списка всех задач
    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Получение задачи по ID
    @Transactional
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Задача по указанному ID не найдено..."));
    }

    // продление дедлайна
    @Transactional
    public Task dateExtension(Long id, DateExtensionDto dateExtensionDto) {
        Task task = getTask(id);

        task.setTargetDate(dateExtensionDto.getTargetDate());

        taskRepository.save(task);

        NotificationDto notificationDto = new NotificationDto(task);
        notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_DEADLINE_EXTENDED);

        return task;
    }

    // Удаление задачи
    @Transactional
    public void deleteTask(Long id) {
        Task task = getTask(id);

        taskRepository.delete(task);

        if (task.getFinishDate() != null) {
            NotificationDto notificationDto = new NotificationDto(task);
            notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_DELETED);
        }
    }

    // Завершение задачи
    // 1. изменяем статус задачи на COMPLETED
    // 2. уведомляем всех о завершении задачи
    @Transactional
    public void completedTask(Long id) {
        Task task = getTask(id);

        completedTask(task);
    }

    @Transactional
    public void completedTask(Task task) {
        if (task.getFinishDate() != null) {
            throw new AlreadyExists("Задача завершена, в повторном завершении задачи нет необходимости");
        }

        task.setFinishDate(LocalDateTime.now());
        task.setStatus(Status.COMPLETED);

        taskRepository.save(task);

        NotificationDto notificationDto = new NotificationDto(task);
        notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_COMPLETED);
    }

    // 1. получение задачи
    // 2. намечаем статус задачи, как IN_PROGRESS
    // 3. отправляем уведомление руководителю о начале решения задачи
    @Transactional
    public void startTaskExecution(Long id, Long userId) {
        Task task = getTask(id);

//        checkResponsibility(task.getResponsibles(), userId);

        if (task.getStatus() != Status.CREATED) {
            throw new AlreadyExists("Задача уже находится в процессе выполнения или завершена. Повторный старт задачи невозможен.");
        }

        task.setStatus(Status.IN_PROGRESS);

        taskRepository.save(task);

        NotificationDto notificationDto = new NotificationDto(task);
        notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_ACCEPTED_STARTED);
    }

    // 1. отправляем решение на проверку
    // 2. намечаем статус решение, как SUBMITTED
    // 3. отправляем уведомление руководителю о получение решения задачи
    public void submitTaskResolution(Long id, AddResolutionDto addResolutionDto) {
        Task task = getTask(id);

        checkResponsibility(task.getResponsibles(), addResolutionDto.getAuthorID());

        resolutionService.addResolution(task, addResolutionDto);

        NotificationDto notificationDto = new NotificationDto(task);
        notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_RESOLUTION_RECEIVED);
    }


    public void taskStatus(Long id, StatusDto statusDto) {
        Task task = getTask(id);
        if (task.getFinishDate() != null) {
            throw new AlreadyExists("Задача завершена, в повторном действии нет необходимости");
        }

        resolutionService.updateResolutionStatus(task, statusDto);
        NotificationDto notificationDto = new NotificationDto(task);

        switch (statusDto.getStatus()) {
            case "APPROVED" -> {
                task.setFinishDate(LocalDateTime.now());
                task.setStatus(Status.COMPLETED);

                taskRepository.save(task);

                notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_RESOLUTION_APPROVED);
            }
            case "FAILED" -> {

                notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_RESOLUTION_REVISION);
            }
        }
    }

    @Transactional
    public List<Task> getActiveTasksByAuthor(Long id) {
        User user = userService.findById(id);

        return taskRepository.findByUserAndFinishDateIsNull(user);
    }

    //    Получение списка активных задач, созданных пользователем
    @Transactional
    public List<Task> getActiveTasksByAssignee(Long id) {
        User user = userService.findById(id);

        return taskRepository.findByUserAndFinishDateIsNull(user);
    }

    //    Добавление коммента к задаче
    //    1. Если сотрудник попытается прокомментировать уже закрытую задачу, то вызывается исключение, сообщающая, что это невозможно
    //    2. Если значение указанное в 'isParallel' является 'false', то задача закрывается сразу после того, как прокомментирует один из сотрудников
    @Transactional
    public void addCommentToTask(Long id, AddResolutionDto commentDto) {
        Task task = getTask(id);

        checkResponsibility(task.getResponsibles(), commentDto.getAuthorID());

        if (task.getFinishDate() != null)
            throw new AlreadyExists("Невозможно добавить комментарий к закрытой задаче...");

        resolutionService.addComment(task, commentDto);

        NotificationDto notificationDto = new NotificationDto(task, commentDto.getAuthorID());
        notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_COMMENT_ADDED);

        if (!task.getIsParallel() || resolutionService.getComments(task).size() == task.getResponsibles().length) {
            notificationProducer.sendNotification(notificationDto, TaskNotificationTopic.TASK_COMMENT_CLOSED);

            completedTask(task);
        }
    }

    //    Получение комментариев к задаче
    @Transactional
    public List<Resolution> getCommentsToTask(Long id) {
        Task task = getTask(id);

        return resolutionService.getComments(task);
    }


    private void checkResponsibility(Long[] responsibles, Long id) {
        for (Long item : responsibles) {
            if (item == id) return;
        }

        throw new ResourceNotFound("Вы не назначены к задаче, какие-либо действия над этой задачей невозможны...");
    }
}
