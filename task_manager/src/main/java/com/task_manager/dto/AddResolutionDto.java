package com.task_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddResolutionDto {
    private Long taskId;
    private Long id;
    private Long authorID;
    private LocalDateTime creationDate;
    private String text;
}
