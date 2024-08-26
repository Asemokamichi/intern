package com.file_manager.File_Manager.controllers;

import com.file_manager.File_Manager.dto.Group;
import com.file_manager.File_Manager.enums.Permission;
import com.file_manager.File_Manager.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestParam String name, @RequestBody List<Permission> permissions){
        Group group = groupService.createGroup(name, permissions);
        return ResponseEntity.ok(group);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestParam String name,
                                             @RequestBody List<Permission> permissions){
        Group group = groupService.updateGroup(id, name, permissions);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}
