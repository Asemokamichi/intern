package com.task_manager.dto;

import com.task_manager.entity.Resolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResolutionDto {
    private Long id;
    private LocalDateTime creationDate;
    private String text;
    private UserDto user;

    public ResolutionDto(Resolution resolution) {
        id = resolution.getId();
        creationDate = resolution.getCreationDate();
        text = resolution.getText();
        user = new UserDto(resolution.getUser());
    }
}
