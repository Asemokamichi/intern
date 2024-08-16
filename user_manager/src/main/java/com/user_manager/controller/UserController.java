package com.user_manager.controller;

import com.user_manager.dto.UserCreationRequest;
import com.user_manager.dto.UserInfoDto;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.User;
import com.user_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody  UserCreationRequest request) throws NotFoundException {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping()
    public ResponseEntity<User> editUser(@RequestBody UserInfoDto request) throws NotFoundException{
        return ResponseEntity.ok(userService.editUserInfo(request));
    }

    @PutMapping("/{userId}/position/{position}")
    public ResponseEntity<String> updateUserPosition(@PathVariable Long userId, @PathVariable String position) throws NotFoundException{
        return ResponseEntity.ok(userService.updateUserPosition(userId, position));

    }
    @PutMapping("/{userId}/role/{role}")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @PathVariable String role) throws NotFoundException{
        return ResponseEntity.ok(userService.updateUserRole(userId, role));
    }

    @PutMapping("/{userId}/department/{departmentId}")
    public ResponseEntity<String> updateUserDepartment(@PathVariable Long userId, @PathVariable Long departmentId) throws NotFoundException{
        return ResponseEntity.ok(userService.updateUserDepartment(userId, departmentId));
    }

    @PutMapping("/{userId}/activate")
    public ResponseEntity<String> activateUser(@PathVariable Long userId) throws NotFoundException{
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) throws NotFoundException{
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId) throws NotFoundException{
        return ResponseEntity.ok(userService.delete(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getInfo(@PathVariable Long userId) throws NotFoundException{
        return ResponseEntity.ok(userService.getInfo(userId));
    }

    @GetMapping("all/department/{departmentId}")
    public ResponseEntity<List<User>> getAllUserOfDepartment(@PathVariable Long departmentId) throws NotFoundException {
        return ResponseEntity.ok(userService.getAllUserOfDepartment(departmentId));
    }

}
