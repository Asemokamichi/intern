package com.user_manager.service.impl;

import com.user_manager.dto.DepartmentRequest;
import com.user_manager.dto.DepartmentTreeDto;
import com.user_manager.dto.NotificationDto;
import com.user_manager.dto.SingleDepartmentDto;
import com.user_manager.enums.UserNotificationTopic;
import com.user_manager.exception.NotFoundException;
import com.user_manager.kafka.producer.NotificationProducer;
import com.user_manager.mapper.DepartmentMapper;
import com.user_manager.model.Department;
import com.user_manager.repository.DepartmentRepository;
import com.user_manager.service.DepartmentService;
import com.user_manager.service.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.user_manager.utility.TimeFormatter.formatter;
import static com.user_manager.utility.TimeFormatter.now;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserUtilService userUtilService;
    private final NotificationProducer notificationProducer;

    @Override
    public Department getDepartmentById(Long id) throws NotFoundException {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with " + id + " does not exist"));
    }

    @Override
    public SingleDepartmentDto createDepartment(DepartmentRequest request) throws NotFoundException {
        Long headId = request.getHeadId();
        Long parentDepartmentId = request.getParentDepartmentId();
        Department parentDepartment = null;

        if (parentDepartmentId != null) {
            exists(parentDepartmentId);
            parentDepartment = getDepartmentById(parentDepartmentId);

        }
        if (headId != null) {
            userUtilService.exists(headId);
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


        NotificationDto notificationDto = new NotificationDto(department, userUtilService.getAllUserIds());
        notificationProducer.sendNotification(notificationDto, UserNotificationTopic.DEPARTMENT_CREATED);


        return  DepartmentMapper.entityToSingleDepartmentDto((department));
    }

    @Override
    public SingleDepartmentDto updateDepartment(DepartmentRequest request) throws NotFoundException {
        Department department = getDepartmentById(request.getId());

        Long headId = department.getHeadId();
        Department parentDepartment = department.getParentDepartment();
        String title = department.getTitle();

        if (request.getHeadId() != null) {
            if(userUtilService.exists(request.getHeadId())){
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
        NotificationDto notificationDto = new NotificationDto(department, userUtilService.getAllUserIds());
        notificationProducer.sendNotification(notificationDto, UserNotificationTopic.DEPARTMENT_UPDATE);

        return  DepartmentMapper.entityToSingleDepartmentDto((department));
    }

    @Override
    public String deleteDepartmentHead(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);

        department.setHeadId(null);
        department.setModificationDate(now.format(formatter));
        departmentRepository.save(department);

        //notification about department head delete
        NotificationDto notificationDto = new NotificationDto(department, userUtilService.getAllUserIds());
        notificationProducer.sendNotification(notificationDto, UserNotificationTopic.DEPARTMENT_HEAD_DELETE);
        return "Head id is deleted";
    }

    @Override
    public String deleteParentDepartment(Long id) throws NotFoundException{
        Department department = getDepartmentById(id);
        department.setParentDepartment(null);
        department.setModificationDate(now.format(formatter));
        departmentRepository.save(department);

        //notification about department parent delete
        NotificationDto notificationDto = new NotificationDto(department, userUtilService.getAllUserIds());
        notificationProducer.sendNotification(notificationDto, UserNotificationTopic.DEPARTMENT_PARENT_DELETE);
        return "Parent Department id is deleted";
    }


    @Override
    public String delete(Long id) throws NotFoundException {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
        //notification about department  delete
        NotificationDto notificationDto = new NotificationDto(department, userUtilService.getAllUserIds());
        notificationProducer.sendNotification(notificationDto, UserNotificationTopic.DEPARTMENT_DELETE);
        return "Department is deleted";
    }

    @Override
    public SingleDepartmentDto getDepartment(Long id) throws NotFoundException {
        return DepartmentMapper.entityToSingleDepartmentDto(getDepartmentById(id));
    }

    @Override
    public DepartmentTreeDto buildDepartmentTree(Long departmentId) throws NotFoundException {
        Department root = getDepartmentById(departmentId);

        return DepartmentMapper.entityToDepartmentTreeDto(root);
    }

    public Boolean exists(Long id) throws NotFoundException {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department with " + id + " does not exist");
        }
        return true;
    }


}
