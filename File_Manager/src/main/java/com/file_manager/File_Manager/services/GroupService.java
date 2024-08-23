package com.file_manager.File_Manager.services;

import com.file_manager.File_Manager.dto.Group;
import com.file_manager.File_Manager.dto.Permission;
import com.file_manager.File_Manager.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group createGroup(String name, List<Permission> permissions) {
        try {
            Group group = new Group();
            group.setName(name);
            group.setPermissions(permissions);
            return groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create group: " + e.getMessage());
        }
    }

    public Group updateGroup(Long groupId, String name, List<Permission> permissions) {
        try {
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            group.setName(name);
            group.setPermissions(permissions);
            return groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Failed to edit group: " + e.getMessage());
        }
    }

    public void deleteGroup(Long groupId) {
        try {
            groupRepository.deleteById(groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete group: " + e.getMessage());
        }
    }
}
