package com.user_manager.dto;

import com.user_manager.enums.Role;
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
