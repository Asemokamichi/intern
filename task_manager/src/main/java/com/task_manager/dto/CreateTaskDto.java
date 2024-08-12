package com.task_manager.dto;

import com.task_manager.enums.TypeTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
    private LocalDateTime creationDate;
    private LocalDateTime targetDate;
    private Long authorID;
    private Long[] responsibles;
    private String type;
    private Boolean isParallel;
    private String title;
    private String body;

    public boolean checkValidation() {
        try {
            TypeTask.valueOf(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }

        return targetDate != null && authorID != null
                && (responsibles != null || responsibles.length!=0) && isParallel != null && title != null && body != null;
    }


}