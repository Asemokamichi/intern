package com.user_manager.service.impl;

import com.user_manager.dto.DepartmentRequest;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Department;
import com.user_manager.repository.DepartmentRepository;
import com.user_manager.service.DepartmentService;
import com.user_manager.service.UserService;
import com.user_manager.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.user_manager.utility.TimeFormatter.formatter;
import static com.user_manager.utility.TimeFormatter.now;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserValidationService userValidationService;

    @Override
    public Department getDepartmentById(Long id) throws NotFoundException {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with " + id + " does not exist"));
    }

    @Override
    public Department createDepartment(DepartmentRequest request) throws NotFoundException {
        Long headId = request.getHeadId();
        Long parentDepartmentId = request.getParentDepartmentId();

        if (parentDepartmentId != null) {
            exists(parentDepartmentId);
        }
        if (headId != null) {
            userValidationService.exists(headId);
        }

        Department department = Department.builder()
                .title(request.getTitle())
                .headId(headId)
                .parentDepartmentId(parentDepartmentId)
                .creationDate(now.format(formatter))
                .modificationDate(now.format(formatter))
                .build();
        departmentRepository.save(department);

        return department;
    }

    @Override
    public Department updateDepartment(Long id, DepartmentRequest request) throws NotFoundException {
        Department department = getDepartmentById(id);

        Long headId = department.getHeadId();
        Long parentDepartmentId = department.getParentDepartmentId();
        String title = department.getTitle();

        if (request.getHeadId() != null) {
            if(userValidationService.exists(request.getHeadId())){
               headId = request.getHeadId();
            }
        }

        if (request.getParentDepartmentId() != null) {
            if(exists(request.getParentDepartmentId())){
                parentDepartmentId = request.getParentDepartmentId();
            }
        }

        if(request.getTitle() != null){
            title = request.getTitle();
        }

        department.setTitle(title);
        department.setHeadId(headId);
        department.setParentDepartmentId(parentDepartmentId);
        department.setModificationDate(now.format(formatter));

        departmentRepository.save(department);
        return department;
    }

    @Override
    public String deleteDepartmentHead(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);

        department.setHeadId(null);
        department.setModificationDate(now.format(formatter));
        departmentRepository.save(department);
        return "Head id is deleted";
    }

    @Override
    public String deleteParentDepartment(Long id) throws NotFoundException{
        Department department = getDepartmentById(id);
        department.setParentDepartmentId(null);
        department.setModificationDate(now.format(formatter));
        departmentRepository.save(department);
        return "Parent Department id is deleted";
    }


    @Override
    public String delete(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
        return "Department is deleted";
    }

    @Override
    public List<Department> getDepartments(Long id) {
        return List.of();
    }

    @Override
    public List<Department> getDepartments() {
        return List.of();
    }

    public Boolean exists(Long id) throws NotFoundException {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department with " + id + " does not exist");
        }
        return true;
    }

}
