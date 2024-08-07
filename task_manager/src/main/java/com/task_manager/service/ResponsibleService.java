package com.task_manager.service;

import com.task_manager.entity.Responsible;
import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.exceptions.ResourceNotFound;
import com.task_manager.repository.ResponsibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface ResponsibleService {
    //    получаем назначенных юзеров, прикрепляем созданную задачу к ним и отправляем в бд
    void assignUsersToTask(List<Long> responsibleUsersId, Task task) throws ResourceNotFound;
}
