package com.task_manager.service.impl;

import com.task_manager.dto.AddResolutionDto;
import com.task_manager.dto.StatusDto;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import com.task_manager.entity.User;
import com.task_manager.enums.ResolutionStatus;
import com.task_manager.exceptions.AlreadyExists;
import com.task_manager.exceptions.ResourceNotFound;
import com.task_manager.repository.ResolutionRepository;
import com.task_manager.service.ResolutionService;
import com.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResolutionServiceImpl implements ResolutionService {
    @Autowired
    private ResolutionRepository resolutionRepository;

    @Autowired
    private UserService userService;

    //    Добавление комментария к задаче
    @Transactional
    public Resolution addComment(Task task, AddResolutionDto commentDto) {
        User user = userService.findById(commentDto.getAuthorID());
        if (resolutionRepository.existsByUserAndTask(user, task))
            throw new AlreadyExists("Вы уже оставляли комметарий, в повторном комментировании нет необходимости");

        Resolution resolution = new Resolution(commentDto);

        resolution.setTask(task);
        resolution.setUser(user);

        return resolutionRepository.save(resolution);
    }

    //    Добавление решения к задаче
    @Transactional
    public Resolution addResolution(Task task, AddResolutionDto resolutionDto) {
        User user = userService.findById(resolutionDto.getAuthorID());

        if (resolutionRepository.existsByUserAndTaskWithStatusSubmittedOrApproved(user, task)) {
            throw new AlreadyExists("Ваше решение уже принято или находится на проверке. Повторная отправка не требуется.");
        }

        Resolution resolution = new Resolution(resolutionDto);

        resolution.setTask(task);
        resolution.setUser(user);
        resolution.setStatus(ResolutionStatus.SUBMITTED);

        return resolutionRepository.save(resolution);
    }

    //    Получение комментариев к задаче
    @Transactional
    public List<Resolution> getComments(Task task) {
        return resolutionRepository.findByTask(task);
    }

    //помечает статус решение как "Failed" или "Approved"
    @Transactional
    public void updateResolutionStatus(Task task, StatusDto statusDto) {
        Resolution resolution = resolutionRepository.findById(statusDto.getResolutionId())
                        .orElseThrow(()->new ResourceNotFound("Решение не найдено, повторите запрос..."));

        if (resolution.getTask() != task) {
            throw new ResourceNotFound("Это решение не относиться к это задаче, повторите запрос...");
        }

        if (resolution.getStatus() != ResolutionStatus.SUBMITTED) {
            throw new ResourceNotFound("Решение уже рассмотрено, повторите запрос...");
        }

        resolution.setStatus(ResolutionStatus.valueOf(statusDto.getStatus()));

        resolutionRepository.save(resolution);
    }
}
