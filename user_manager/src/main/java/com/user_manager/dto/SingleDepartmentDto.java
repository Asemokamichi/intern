package com.user_manager.dto;

import com.user_manager.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleDepartmentDto {
    private String title;
    private Long headId;
    private Long parentDepartmentId;
    private String creationDate;
    private String modificationDate;

    public static SingleDepartmentDto fromEntity(Department department){
        SingleDepartmentDto dto = new SingleDepartmentDto();
        dto.setTitle(department.getTitle());
        dto.setHeadId(department.getHeadId());
        dto.setCreationDate(department.getCreationDate());
        dto.setModificationDate(department.getModificationDate());
        if (department.getParentDepartment() != null) {
            dto.setParentDepartmentId(department.getParentDepartment().getId());
        }

        return dto;

    }

}
