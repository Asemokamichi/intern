package com.task_manager.service;

import com.task_manager.dto.AddResolutionDto;
import com.task_manager.dto.StatusDto;
import com.task_manager.entity.Resolution;
import com.task_manager.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResolutionService {
    //    Добавление комментария к задаче
    Resolution addComment(Task task, AddResolutionDto commentDto);

    //    Добавление решения к задаче
    Resolution addResolution(Task task, AddResolutionDto resolutionDto);

    //    Получение комментариев к задаче
    List<Resolution> getComments(Task task);

    //помечает статус решение как "Failed" или "Approved"
    void updateResolutionStatus(Task task, StatusDto statusDto);
}
