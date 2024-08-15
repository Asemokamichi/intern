package com.user_manager.service.impl;

import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Department;
import com.user_manager.repository.UserRepository;
import com.user_manager.service.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUtilServiceImpl implements UserUtilService {
    private final UserRepository userRepository;

    @Override
    public Boolean exists(Long id) throws NotFoundException {
        if(!userRepository.existsById(id)){
            throw new NotFoundException("User with " + id + " does not exist");
        }
        return true;
    }

    @Override
    public List<Long> getAllUserIds() {
        return userRepository.findAllUserIds();
    }

    @Override
    public List<Long> findAllUserIdsOfDepartment(Department department) {
        return userRepository.findAllUserIdsOfDepartment(department);
    }

}
