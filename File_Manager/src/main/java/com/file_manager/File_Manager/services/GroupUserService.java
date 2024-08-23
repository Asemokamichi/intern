package com.file_manager.File_Manager.services;

import com.file_manager.File_Manager.dto.GroupUser;
import com.file_manager.File_Manager.feign_client.UserClient;
import com.file_manager.File_Manager.repositories.GroupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupUserService {

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private UserClient userClient;

    public void addUserToGroup(Long groupId, Set<Long> userIds) {
        try {
            Set<Long> nonExistentUsers = userClient.findNonExistingUserIds(userIds);

            if(!nonExistentUsers.isEmpty()){
                throw new RuntimeException("Users with these IDs were not found: " + nonExistentUsers);
            }

            GroupUser groupUsers = groupUserRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            Set<Long> existingUsers = new HashSet<>(groupUsers.getUsers());
            existingUsers.addAll(userIds);
            groupUsers.setUsers(existingUsers);
            groupUserRepository.save(groupUsers);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to add users: " + e.getMessage());
        }
    }

    public void removeUserFromGroup(Long groupId, Set<Long> userIds) {
        try {
            Set<Long> nonExistentUsers = userClient.findNonExistingUserIds(userIds);

            if(!nonExistentUsers.isEmpty()){
                throw new RuntimeException("Users with these IDs were not found: " + nonExistentUsers);
            }

            GroupUser groupUsers = groupUserRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            Set<Long> existingUsers = new HashSet<>(groupUsers.getUsers());
            existingUsers.removeAll(userIds);
            groupUsers.setUsers(existingUsers);
            groupUserRepository.save(groupUsers);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to remove users: " + e.getMessage());
        }
    }
}
