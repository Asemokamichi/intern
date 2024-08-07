package com.task_manager.service;

import com.task_manager.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

     User getCurrentUser() ;

     User findById(Long id) ;

     User findByUsername(String username);
}
