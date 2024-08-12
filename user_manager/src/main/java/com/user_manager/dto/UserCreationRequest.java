package com.user_manager.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_manager.model.Department;
import com.user_manager.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
    private String firstName;
    private String lastName;
    private Map<String, String> contacts;
    private String position;
    private Role role;
    private Long departmentId;

}
