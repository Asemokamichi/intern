package com.task_manager.service.impl;

import com.task_manager.dto.AddResolutionDto;
import com.task_manager.dto.CreateTaskDto;
import com.task_manager.dto.DateExtensionDto;
import com.task_manager.dto.StatusDto;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Responsible;
import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import com.task_manager.enums.NotificationType;
import com.task_manager.enums.Status;
import com.task_manager.enums.TypeTask;
import com.task_manager.exceptions.AlreadyExists;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.exceptions.ResourceNotFound;
import com.task_manager.repository.TaskRepository;
import com.task_manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponsibleService responsibleService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ResolutionService resolutionService;

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

        if (createTaskDto.getType().equals("ASSIGNMENT") && createTaskDto.getResponsibles().size() > 1) {
            throw new InvalidRequest("Для задач типа ASSIGNMENT можно назначать только одного сотрудника. Измените тип задачи или разделите задачу для каждого сотрудника.");
        }

        Task task = new Task(createTaskDto);
        task.setUser(user);
        task.setStatus(Status.CREATED);

        if (task.getTypeTask() == TypeTask.NOTIFICATION) {
            task.setFinishDate(LocalDateTime.now());
        }

        taskRepository.save(task);


        responsibleService.assignUsersToTask(createTaskDto.getResponsibles(), task);


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

        notificationService.notifyAllAboutDeadlineExtension(task);

        return task;
    }

    // Удаление задачи
    @Transactional
    public void deleteTask(Long id) {
        Task task = getTask(id);

        taskRepository.delete(task);

        if (task.getFinishDate() != null) notificationService.notifyTaskDeleted(task);
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

        notificationService.notifyTaskCompleted(task);
    }

    // 1. получение задачи
    // 2. намечаем статус задачи, как IN_PROGRESS
    // 3. отправляем уведомление руководителю о начале решения задачи
    @Transactional
    public void startTaskExecution(Long id, Long userId) {
        Task task = getTask(id);

        checkResponsibility(task.getResponsibles(), userId);

        if (task.getStatus() != Status.CREATED) {
            throw new AlreadyExists("Задача уже находится в процессе выполнения или завершена. Повторный старт задачи невозможен.");
        }

        task.setStatus(Status.IN_PROGRESS);

        taskRepository.save(task);

        notificationService.notifyManagerAboutTaskAcceptance(task);
    }

    // 1. отправляем решение на проверку
    // 2. намечаем статус решение, как SUBMITTED
    // 3. отправляем уведомление руководителю о получение решения задачи
    public void submitTaskResolution(Long id, AddResolutionDto addResolutionDto) {
        Task task = getTask(id);

        checkResponsibility(task.getResponsibles(), addResolutionDto.getAuthorID());

        resolutionService.addResolution(task, addResolutionDto);

        notificationService.notifyResolutionReceived(task);
    }


    public void taskStatus(Long id, StatusDto statusDto) {
        Task task = getTask(id);
        if (task.getFinishDate() != null) {
            throw new AlreadyExists("Задача завершена, в повторном действии нет необходимости");
        }

        resolutionService.updateResolutionStatus(task, statusDto);

        switch (statusDto.getStatus()) {
            case "APPROVED" -> {
                task.setFinishDate(LocalDateTime.now());
                task.setStatus(Status.COMPLETED);

                taskRepository.save(task);

                notificationService.notifyTaskResolutionApproved(task);
            }
            case "FAILED" -> {
                notificationService.notifyTaskResolutionReturnedForRevision(task);
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

        return taskRepository.findByFinishDateIsNullAndResponsiblesUser(user);
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

        Resolution resolution = resolutionService.addComment(task, commentDto);

        notificationService.notifyNewCommentAdded(resolution);

        if (!task.getIsParallel() || resolutionService.getComments(task).size() == task.getResponsibles().size()) {
            if (resolutionService.getComments(task).size() == task.getResponsibles().size()) {
                notificationService.notifySupervisorTaskClosed(task, NotificationType.ALL_EMPLOYEES_COMMENTED);

            } else {
                notificationService.notifySupervisorTaskClosed(task, NotificationType.PARTIAL_EMPLOYEES_COMMENTED);

            }

            completedTask(task);
        }
    }

    //    Получение комментариев к задаче
    @Transactional
    public List<Resolution> getCommentsToTask(Long id) {
        Task task = getTask(id);

        return resolutionService.getComments(task);
    }

    private void checkResponsibility(List<Responsible> responsibles, Long id) {
        for(Responsible responsible: responsibles){
            if (responsible.getUser().getId() == id) return;
        }

        throw new AlreadyExists("Вы не назначены к задаче, вы не можете отвечать...");
    }


}
