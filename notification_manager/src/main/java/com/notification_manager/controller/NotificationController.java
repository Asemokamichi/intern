package com.notification_manager.controller;

import com.notification_manager.entity.Notification;
import com.notification_manager.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@AllArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "Получить все уведомления",
            description = "Получает все уведомления для указанного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Уведомления успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotifications(userId);

        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Пометить уведомление как прочитанное",
            description = "Помечает указанное уведомление как прочитанное для пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Уведомление успешно помечено как прочитанное."),
            @ApiResponse(responseCode = "404",
                    description = "Уведомление или пользователь не найден.")
    })
    @GetMapping("/{userId}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long userId, @RequestParam Long noteId) {
        notificationService.markNotificationAsRead(userId, noteId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Пометить все уведомления как прочитанные",
            description = "Помечает все уведомления как прочитанные для указанного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Все уведомления успешно помечены как прочитанные."),
            @ApiResponse(responseCode = "404",
                    description = "Пользователь не найден.")
    })
    @GetMapping("/{userId}/readAll")
    public ResponseEntity<?> markAllNotificationsAsRead(@PathVariable Long userId) {
        notificationService.markAllNotificationsAsRead(userId);

        return ResponseEntity.ok().build();
    }


    /**
     * Очищает прочитанные уведомления.
     * <p>
     * Данный функционал намеренно реализован без использования очередей,
     * так как текущая нагрузка на систему позволяет выполнять эти операции
     * асинхронно с помощью потока, не перегружая основной поток исполнения.
     * <p>
     * Метод использует {@link ExecutorService} для запуска задачи очистки
     * уведомлений в отдельном потоке.
     */
    @Operation(summary = "Очищает прочитанные уведомления.",
            description = "Очищает прочитанные уведомления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Все уведомления успешно помечены как прочитанные."),
            @ApiResponse(responseCode = "404",
                    description = "Пользователь не найден.")
    })
    @GetMapping("/clear")
    public ResponseEntity<?> clearNotification() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Runnable runnable = () -> {
            notificationService.clearReadNotifications();
        };

        executorService.submit(runnable);

        executorService.shutdown();

        return ResponseEntity.ok().build();
    }
}
