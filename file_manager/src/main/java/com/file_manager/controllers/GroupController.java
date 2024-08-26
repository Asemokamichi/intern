package com.file_manager.controllers;


import com.file_manager.dto.Group;
import com.file_manager.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.file_manager.enums.Permission;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
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
