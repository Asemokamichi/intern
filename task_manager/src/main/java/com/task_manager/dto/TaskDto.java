package com.task_manager.dto;

import com.task_manager.entity.Resolution;
import com.task_manager.entity.Responsible;
import com.task_manager.entity.Task;
import com.task_manager.util.Convert;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime targetDate;
    private LocalDateTime finishDate;
    private String status;
    private String typeTask;
    private Boolean isParallel;
    private String title;
    private String body;
    private Long userId;
    private List<Long> responsibles;
    private List<Long> resolutions;

    public TaskDto(Task task) {
        id = task.getId();
        creationDate = task.getCreationDate();
        targetDate = task.getTargetDate();
        finishDate = task.getFinishDate();
        status = task.getStatus().name();
        typeTask = task.getTypeTask().name();
        isParallel = task.getIsParallel();
        title = task.getTitle();
        body = task.getBody();
        if (task.getUser() != null) userId = task.getUser().getId();

        if (task.getResolutions()!=null){
            resolutions = new ArrayList<>();
            for (Resolution res: task.getResolutions()) resolutions.add(res.getUser().getId());
        }

        if (task.getResolutions()!=null){
            responsibles = new ArrayList<>();
            for (Responsible res: task.getResponsibles()) responsibles.add(res.getUser().getId());
        }

    }
}
