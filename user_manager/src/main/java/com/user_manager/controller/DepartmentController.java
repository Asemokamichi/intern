package com.user_manager.controller;

import com.user_manager.dto.DepartmentRequest;
import com.user_manager.dto.DepartmentTreeDto;
import com.user_manager.dto.SingleDepartmentDto;
import com.user_manager.exception.NotFoundException;
import com.user_manager.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping()
    public ResponseEntity<SingleDepartmentDto> createDepartment(@RequestBody DepartmentRequest request) throws NotFoundException {
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }

    @PutMapping()
    public ResponseEntity<SingleDepartmentDto> updateDepartment(@RequestBody DepartmentRequest request) throws NotFoundException {
        return ResponseEntity.ok(departmentService.updateDepartment(request));
    }

    @DeleteMapping("/{departmentId}/head")
    public ResponseEntity<String> deleteDepartmentHead(@PathVariable Long departmentId) throws NotFoundException {
        return ResponseEntity.ok(departmentService.deleteDepartmentHead(departmentId));
    }

    @DeleteMapping("/{departmentId}/parent")
    public ResponseEntity<String> deleteParentDepartment(@PathVariable Long departmentId) throws NotFoundException {
        return ResponseEntity.ok(departmentService.deleteParentDepartment(departmentId));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> delete(@PathVariable Long departmentId) throws NotFoundException {
        return ResponseEntity.ok(departmentService.delete(departmentId));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<SingleDepartmentDto> getDepartment(@PathVariable Long departmentId) throws NotFoundException{
            return ResponseEntity.ok(departmentService.getDepartment(departmentId));
    }

    @GetMapping("/{departmentId}/all")
    public ResponseEntity<DepartmentTreeDto> getDepartmentTree(@PathVariable Long departmentId) throws NotFoundException {
        return ResponseEntity.ok(departmentService.buildDepartmentTree(departmentId));
    }

}
