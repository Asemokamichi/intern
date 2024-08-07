package com.task_manager.dto;

import com.task_manager.entity.Responsible;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsibleDto {
    private Long userId;

    public ResponsibleDto(Responsible responsible) {
        userId = responsible.getUser().getId();
    }
}
