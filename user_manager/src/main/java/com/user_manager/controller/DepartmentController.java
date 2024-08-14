package com.user_manager.controller;

import com.user_manager.dto.DepartmentRequest;
import com.user_manager.dto.DepartmentTreeDto;
import com.user_manager.dto.SingleDepartmentDto;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Department;
import com.user_manager.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-manager")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

        @PostMapping("/department")
    public ResponseEntity<SingleDepartmentDto> createDepartment(@RequestBody DepartmentRequest request) throws NotFoundException {
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }

    @PutMapping("/department")
    public ResponseEntity<SingleDepartmentDto> updateDepartment(@RequestParam Long id, @RequestBody DepartmentRequest request) throws NotFoundException {
        return ResponseEntity.ok(departmentService.updateDepartment(id, request));
    }

    @PutMapping("/delete-department-head")
    public ResponseEntity<String> deleteDepartmentHead(@RequestParam Long id) throws NotFoundException {
        return ResponseEntity.ok(departmentService.deleteDepartmentHead(id));
    }

    @PutMapping("/delete-parent-department")
    public ResponseEntity<String> deleteParentDepartment(@RequestParam Long id) throws NotFoundException {
        return ResponseEntity.ok(departmentService.deleteParentDepartment(id));
    }

    @DeleteMapping("/delete-department")
    public ResponseEntity<String> delete(@RequestParam Long id) throws NotFoundException {
        return ResponseEntity.ok(departmentService.delete(id));
    }

    @GetMapping("/department")
    public ResponseEntity<SingleDepartmentDto> getDepartment(@RequestParam Long id) throws NotFoundException{
            return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @GetMapping("/departments")
    public ResponseEntity<DepartmentTreeDto> getDepartmentTree(@RequestParam Long id) throws NotFoundException {
        return ResponseEntity.ok(departmentService.buildDepartmentTree(id));
    }

}
