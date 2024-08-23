package com.file_manager.File_Manager.services;

import com.file_manager.File_Manager.dto.GroupUser;
import com.file_manager.File_Manager.dto.User;
import com.file_manager.File_Manager.repositories.GroupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroupUserService {

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String userManagerUrl = "";

    public List<Long> addUserToGroup(Long groupId, List<Long> userIds) {
        List<Long> nonExistentUsers = new ArrayList<>();

        try {
            GroupUser groupUsers = groupUserRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            List<Long> existingUsers = new ArrayList<>(groupUsers.getUsers());

            for (Long userId : userIds) {
                if (userExists(userId)) {
                    existingUsers.add(userId);
                } else {
                    nonExistentUsers.add(userId);
                }
            }

            groupUsers.setUsers(existingUsers);
            groupUserRepository.save(groupUsers);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add users: " + e.getMessage());
        }

        return nonExistentUsers;
    }


    public List<Long> removeUsersFromGroup(Long groupId, List<Long> userIds){
        List<Long> nonExistentUsers = new ArrayList<>();

        try{
            GroupUser groupUser = groupUserRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            List<Long> existingUsers = new ArrayList<>(groupUser.getUsers());

            for(Long userId : userIds){
                if(userExists(userId)){
                    existingUsers.remove(userId);
                }
                else{
                    nonExistentUsers.add(userId);
                }
            }
            groupUser.setUsers(existingUsers);
            groupUserRepository.save(groupUser);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to remove users: " + e.getMessage());
        }

        return nonExistentUsers;
    }

    private boolean userExists(Long userId) {
        String url = userManagerUrl + "/users/" + userId;
        try {
            ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
            return response.getStatusCode() == HttpStatus.OK && response.getBody() != null;
        } catch (RestClientException e) {
            return false;
        }
    }
}
