package com.task_manager.service.impl;

import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.exceptions.ResourceNotFound;
import com.task_manager.service.NotificationService;
import com.task_manager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@Service
public class ResponsibleServiceImpl {
//    private final ResponsibleRepository responsibleRepository;
//
//    private final UserService userService;
//
//    private final NotificationService notificationService;
//
//    public ResponsibleServiceImpl(ResponsibleRepository responsibleRepository, UserService userService, NotificationService notificationService) {
//        this.responsibleRepository = responsibleRepository;
//        this.userService = userService;
//        this.notificationService = notificationService;
//    }

    //    получаем назначенных юзеров, прикрепляем созданную задачу к ним и отправляем в бд
//    @Transactional
//    public void assignUsersToTask(List<Long> responsibleUsersId, Task task) throws ResourceNotFound {
//        List<Responsible> responsibles = new ArrayList<>();
//
//        for (long id : responsibleUsersId) {
//            try {
//                Responsible responsible = new Responsible();
//
//                User user = userService.findById(id);
//
//                responsible.setUser(user);
//                responsible.setTask(task);
//
//                responsibles.add(responsible);
//
//                responsibleRepository.save(responsible);
//            } catch (InvalidRequest e) {
//                throw new ResourceNotFound("Указанный сотрудник не найден, повторите запрос...");
//            }
//
//        }
//
//        notificationService.notifyAssignmentOfNewTask(responsibles);
//    }
}
