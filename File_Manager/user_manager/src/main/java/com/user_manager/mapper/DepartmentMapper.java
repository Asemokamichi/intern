package com.user_manager.mapper;

import com.user_manager.dto.DepartmentTreeDto;
import com.user_manager.dto.SingleDepartmentDto;
import com.user_manager.model.Department;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {

    public static SingleDepartmentDto entityToSingleDepartmentDto(Department department){
        SingleDepartmentDto dto = new SingleDepartmentDto();
        dto.setId(department.getId());
        dto.setTitle(department.getTitle());
        dto.setHeadId(department.getHeadId());
        dto.setCreationDate(department.getCreationDate());
        dto.setModificationDate(department.getModificationDate());
        if (department.getParentDepartment() != null) {
            dto.setParentDepartmentId(department.getParentDepartment().getId());
        }

        return dto;
    }

    public static DepartmentTreeDto entityToDepartmentTreeDto(Department department){
        DepartmentTreeDto dto = new DepartmentTreeDto();
        dto.setId(department.getId());
        dto.setTitle(department.getTitle());
        dto.setHeadId(department.getHeadId());
        dto.setCreationDate(department.getCreationDate());
        dto.setModificationDate(department.getModificationDate());

        List<DepartmentTreeDto> childDto = department.getChildDepartments()
                .stream()
                .map(DepartmentMapper::entityToDepartmentTreeDto)
                .collect(Collectors.toList());
        dto.setChildDepartments(childDto);

        return dto;
    }
}
