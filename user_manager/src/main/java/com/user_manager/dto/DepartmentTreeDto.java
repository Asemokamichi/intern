package com.user_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTreeDto {
    private Long id;
    private String title;
    private Long headId;
    private String creationDate;
    private String modificationDate;
    private List<DepartmentTreeDto> childDepartments;

}
