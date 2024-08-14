package com.user_manager.service.impl;

import com.user_manager.exception.NotFoundException;
import com.user_manager.repository.UserRepository;
import com.user_manager.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {
    private final UserRepository userRepository;

    @Override
    public Boolean exists(Long id) throws NotFoundException {
        if(!userRepository.existsById(id)){
            throw new NotFoundException("User with " + id + " does not exist");
        }
        return true;
    }

}
