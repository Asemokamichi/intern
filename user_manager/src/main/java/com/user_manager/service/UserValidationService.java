package com.user_manager.service;

import com.user_manager.exception.NotFoundException;

public interface UserValidationService {
    Boolean exists(Long id) throws NotFoundException;
}
