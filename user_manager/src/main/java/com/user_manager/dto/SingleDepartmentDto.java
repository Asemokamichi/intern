package com.user_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleDepartmentDto {
    private Long id;
    private String title;
    private Long headId;
    private Long parentDepartmentId;
    private String creationDate;
    private String modificationDate;

}
