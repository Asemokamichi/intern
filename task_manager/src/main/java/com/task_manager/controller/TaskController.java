package com.task_manager.controller;

import com.task_manager.dto.*;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import com.task_manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Создание задачи", description = """
            Создает новую задачу на основе предоставленных данных.
            - Руководитель создает задачу.
            - Задача назначается определенным сотрудникам.
            - Назначенным сотрудникам отправляется уведомление.
            - Есть 3 вида задачи: ASSIGNMENT, COMMENT, NOTIFICATION.
                - Если задача является ASSIGNMENT, то на задачу может быть назначен только один сотрудник. Задача считается выполненной только после того, как руководитель примет решение по задаче.
                - Если задача является COMMENT, то задача закрывается только после того, как все назначенные сотрудники или один из них прокомментируют задачу, в зависимости от значения, указанного в 'isParallel'.
                - Если задача является NOTIFICATION, то задача закрывается сразу после создания.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Task.class)
            )),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDto createTaskDto) {
        Task task = taskService.createTask(createTaskDto);

        URI location = URI.create(String.format("/tasks/%d", task.getId()));

        return ResponseEntity.created(location).body(task);
    }


    @Operation(summary = "Получить список всех задач", description = "Возвращает список всех актуальных задач. Если задач нет, возвращается сообщение о их отсутствии.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Task.class)
            )),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllTask() {
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) return ResponseEntity.ok("На данный момент актуальных задач отсутствует");

        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Получение задачи по ID", description = "Возвращает задачу на основе указанного ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно получена", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Task.class)
            )),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);

        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Продление дедлайна задачи", description = """
        Позволяет продлить дедлайн существующей задачи.
        - Задается новый дедлайн для задачи.
        - Если новая дата недействительна, возвращается ошибка.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дедлайн задачи успешно продлен", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Task.class)
            )),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PutMapping("/{taskId}")
    public ResponseEntity<?> dateExtension(@PathVariable Long taskId, @RequestBody DateExtensionDto dateExtensionDto) {
        Task task = taskService.dateExtension(taskId, dateExtensionDto);

        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Удаление задачи", description = "Позволяет удалить задачу по заданному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавление комментария к задаче", description = """
            Добавляет комментарий к указанной задаче.
            - Если сотрудник попытается прокомментировать уже закрытую задачу, то вызывается исключение, сообщающее, что это невозможно.
            - Если значение, указанное в 'isParallel', является 'false', то задача закрывается сразу после того, как прокомментирует один из сотрудников.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addCommentToTask(@PathVariable Long taskId, @RequestBody AddResolutionDto addResolutionDto) {
        taskService.addCommentToTask(taskId, addResolutionDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение комментариев или решении к задаче", description = "Возвращает список комментариев или решении для указанной задачи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии успешно получены", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resolution.class))
            )),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("{taskId}/resolutions")
    public ResponseEntity<?> getCommentsToTask(@PathVariable Long taskId) {
        List<Resolution> resolutions = taskService.getCommentsToTask(taskId);

        return ResponseEntity.ok(resolutions);
    }

    @Operation(summary = "Завершение задачи", description = """
            Завершение задачи
            - Завершает задачу, изменяя её статус на COMPLETED.
            - После завершения задачи уведомляются все назначенные сотрудники.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно завершена", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "409", description = "Задача уже завершена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PutMapping("/{taskId}/completed")
    public ResponseEntity<?> completedTask(@PathVariable Long taskId) {
        taskService.completedTask(taskId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Начало выполнения задачи", description = """
            Начало выполнения задачи.
            - Сотрудник получает задачу.
            - Нажимает на кнопку, указывающую на начало задачи.
            - Статус задачи меняется на IN_PROGRESS.
            - Руководителю отправляется уведомление о начале решения задачи.
              """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно запущена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/{taskId}/start")
    public ResponseEntity<?> startTaskExecution(@PathVariable Long taskId, @RequestParam("userId") Long userId) {
        taskService.startTaskExecution(taskId, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отправка решения задачи на проверку", description = """
            Отправляет решение задачи на проверку руководителю.
            - Получает решение задачи.
            - Отправляет решение на проверку.
            - Устанавливает статус решения как SUBMITTED.
            - Уведомляет руководителя о получении решения задачи.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Решение успешно отправлено на проверку", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/{taskId}/decision")
    public ResponseEntity<?> submitTaskResolution(@PathVariable Long taskId, @RequestBody AddResolutionDto addResolutionDto) {
        taskService.submitTaskResolution(taskId, addResolutionDto);

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Обновление статуса задачи", description = """
        Обновляет статус задачи в зависимости от ответа руководителя.
        - Руководитель проверяет решение и отправляет ответ.
        - Если ответ APPROVED, задача завершается.
        - Если ответ FAILED, задача отправляется на доработку.
        - Статус решения задачи устанавливается в соответствии с ответом.
        - Отправляется соответствующее уведомление.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно обновлен", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/{taskId}/status")
    public ResponseEntity<?> taskStatus(@PathVariable Long taskId, @RequestBody StatusDto statusDto) {
        taskService.taskStatus(taskId, statusDto);

        return ResponseEntity.ok().build();
    }

    //    Получение списка активных задач, созданных пользователем
    @GetMapping("/assignedTo/{userId}")
    public ResponseEntity<?> getActiveTasksByAssignee(@PathVariable Long userId) {
        List<Task> tasks = taskService.getActiveTasksByAssignee(userId);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/createdBy/{authorId}")
    public ResponseEntity<?> getActiveTasksByAuthor(@PathVariable Long authorId) {
        List<Task> tasks = taskService.getActiveTasksByAuthor(authorId);

        return ResponseEntity.ok(tasks);
    }

}
