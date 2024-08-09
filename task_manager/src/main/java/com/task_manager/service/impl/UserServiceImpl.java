package com.task_manager.service.impl;

import com.task_manager.entity.User;
import com.task_manager.exceptions.AlreadyExists;
import com.task_manager.exceptions.InvalidRequest;
import com.task_manager.repository.UserRepository;
import com.task_manager.security.UserDetailsImpl;
import com.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("Указанный сотрудник не найден, повторите операцию"));
    }

    @Transactional
    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AlreadyExists(""));
    }
}
