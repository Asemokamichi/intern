package com.user_manager.controller;

import com.user_manager.dto.UserCreationRequest;
import com.user_manager.dto.UserInfoDto;
import com.user_manager.exception.NotFoundException;
import com.user_manager.model.Role;
import com.user_manager.model.User;
import com.user_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-manager")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody  UserCreationRequest request) throws NotFoundException {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/user")
    public ResponseEntity<User> editUser(@RequestParam Long id, @RequestBody UserInfoDto request) throws NotFoundException{
        return ResponseEntity.ok(userService.editUserInfo(id, request));
    }

    @PutMapping("/update-position")
    public ResponseEntity<String> updateUserPosition(@RequestParam Long id, @RequestParam String position) throws NotFoundException{
        return ResponseEntity.ok(userService.updateUserPosition(id, position));

    }
    @PutMapping("/update-role")
    public ResponseEntity<String> updateUserRole(@RequestParam Long id, @RequestParam Role role) throws NotFoundException{
        return ResponseEntity.ok(userService.updateUserRole(id, role));
    }

    @PutMapping("/update-department")
    public ResponseEntity<String> updateUserDepartment(@RequestParam Long id, @RequestParam Long departmentId) throws NotFoundException{
        return ResponseEntity.ok(userService.updateUserDepartment(id, departmentId));
    }

    @PutMapping("/activate-user")
    public ResponseEntity<String> activateUser(@RequestParam Long id) throws NotFoundException{
        return ResponseEntity.ok(userService.activateUser(id));
    }

    @PutMapping("/deactivate-user")
    public ResponseEntity<String> deactivateUser(@RequestParam Long id) throws NotFoundException{
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> delete(@RequestParam Long id) throws NotFoundException{
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getInfo(@RequestParam Long id) throws NotFoundException{
        return ResponseEntity.ok(userService.getInfo(id));
    }

    @GetMapping("all-users-of-department")
    public ResponseEntity<List<User>> getAllUserOfDepartment(@RequestParam Long departmentId){
        return ResponseEntity.ok(userService.getAllUserOfDepartment(departmentId));
    }

}
