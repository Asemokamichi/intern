package com.notification_manager.notification_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequest  extends RuntimeException{
    public InvalidRequest(String message) {
        super(message);
    }
}
