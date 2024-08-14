package com.user_manager.service.impl;

import com.user_manager.dto.DepartmentRequest;
import com.user_manager.dto.SingleDepartmentDto;
import com.user_manager.enums.NotificationTopic;
import com.user_manager.exception.NotFoundException;
import com.user_manager.kafka.producer.NotificationProducer;
import com.user_manager.model.Department;
import com.user_manager.repository.DepartmentRepository;
import com.user_manager.service.DepartmentService;
import com.user_manager.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.user_manager.utility.TimeFormatter.formatter;
import static com.user_manager.utility.TimeFormatter.now;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserValidationService userValidationService;
    private final NotificationProducer notificationProducer;

    @Override
    public Department getDepartmentById(Long id) throws NotFoundException {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with " + id + " does not exist"));
    }

    @Override
    public Department createDepartment(DepartmentRequest request) throws NotFoundException {
        Long headId = request.getHeadId();
        Long parentDepartmentId = request.getParentDepartmentId();
        Department parentDepartment = null;

        if (parentDepartmentId != null) {
            exists(parentDepartmentId);
            parentDepartment = getDepartmentById(parentDepartmentId);

        }
        if (headId != null) {
            userValidationService.exists(headId);
        }

        Department department = Department.builder()
                .title(request.getTitle())
                .headId(headId)
                .parentDepartment(parentDepartment)
                .creationDate(now.format(formatter))
                .modificationDate(now.format(formatter))
                .build();
        departmentRepository.save(department);

        //notification about department creation
        notificationProducer.sendNotification(department.getId(), NotificationTopic.DEPARTMENT_CREATED);

        return department;
    }

    @Override
    public Department updateDepartment(Long id, DepartmentRequest request) throws NotFoundException {
        Department department = getDepartmentById(id);

        Long headId = department.getHeadId();
        Department parentDepartment = department.getParentDepartment();
        String title = department.getTitle();

        if (request.getHeadId() != null) {
            if(userValidationService.exists(request.getHeadId())){
               headId = request.getHeadId();
            }
        }

        if (request.getParentDepartmentId() != null) {
            Long getParentDepartmentId = request.getParentDepartmentId();
            if(exists(getParentDepartmentId)){
                parentDepartment = getDepartmentById(getParentDepartmentId);
            }
        }

        if(request.getTitle() != null){
            title = request.getTitle();
        }

        department.setTitle(title);
        department.setHeadId(headId);
        department.setParentDepartment(parentDepartment);
        department.setModificationDate(now.format(formatter));

        departmentRepository.save(department);

        //notification about department update
        notificationProducer.sendNotification(id, NotificationTopic.DEPARTMENT_UPDATE);

        return department;
    }

    @Override
    public String deleteDepartmentHead(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);

        department.setHeadId(null);
        department.setModificationDate(now.format(formatter));
        departmentRepository.save(department);

        //notification about department head delete
        notificationProducer.sendNotification(id, NotificationTopic.DEPARTMENT_HEAD_DELETE);
        return "Head id is deleted";
    }

    @Override
    public String deleteParentDepartment(Long id) throws NotFoundException{
        Department department = getDepartmentById(id);
        department.setParentDepartment(null);
        department.setModificationDate(now.format(formatter));
        departmentRepository.save(department);

        //notification about department parent delete
        notificationProducer.sendNotification(id, NotificationTopic.DEPARTMENT_PARENT_DELETE);
        return "Parent Department id is deleted";
    }


    @Override
    public String delete(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
        //notification about department  delete
        notificationProducer.sendNotification(id, NotificationTopic.DEPARTMENT_DELETE);
        return "Department is deleted";
    }

    @Override
    public SingleDepartmentDto getDepartment(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);
        return SingleDepartmentDto.fromEntity(department);
    }

    @Override
    public String buildDepartmentTree(Long departmentId) throws NotFoundException {
        Department root = getDepartmentById(departmentId);
        if (root == null) {
            return "Department with " + departmentId + " does not exist";
        }
        StringBuilder result = new StringBuilder();
        printTree(root, "", result);
        return result.toString();
    }

    private void printTree(Department department, String indent, StringBuilder result) {
        result.append(indent).append("Title: ").append(department.getTitle()).append("\n")
                .append(indent).append("Head ID: ").append(department.getHeadId()).append("\n")
                .append(indent).append("Creation Date: ").append(department.getCreationDate()).append("\n");
        for (Department child : department.getChildDepartments()) {
            printTree(child, indent + "    ", result);
        }
    }

    public Boolean exists(Long id) throws NotFoundException {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department with " + id + " does not exist");
        }
        return true;
    }


}
