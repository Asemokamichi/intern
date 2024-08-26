package com.file_manager.controllers;

import com.file_manager.File_Manager.dto.GroupUser;
import com.file_manager.File_Manager.services.GroupUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groups/users")
public class GroupUserController {

    private final GroupUserService groupUserService;

    @PostMapping("/add")
    public ResponseEntity<GroupUser> addUserToGroup(@RequestParam Long groupId, @RequestBody Set<Long> userIds){
        GroupUser groupUser = groupUserService.addUserToGroup(groupId, userIds);
        return ResponseEntity.ok(groupUser);
    }

    @PostMapping("remove")
    public ResponseEntity<GroupUser> deleteUserFromGroup(@RequestParam Long groupId, @RequestBody Set<Long> userIds){
        GroupUser groupUser = groupUserService.removeUserFromGroup(groupId, userIds);
        return ResponseEntity.ok(groupUser);
    }
}
