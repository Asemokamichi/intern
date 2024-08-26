package com.file_manager.services;


import com.file_manager.dto.FileGroups;
import com.file_manager.repositories.FileGroupRepository;
import com.file_manager.repositories.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class FileGroupService {

    @Autowired
    private final FileGroupRepository fileGroupRepository;

    @Autowired
    private GroupRepository groupRepository;

    public FileGroups giveAccessToFile(String fileId, Set<Long> groupIds){
        try{
            Set<Long> nonExistentGroups = new HashSet<>();

            for(Long groupId : groupIds){
                if(!groupRepository.existsById(groupId)){
                    nonExistentGroups.add(groupId);
                }
            }

            if(!nonExistentGroups.isEmpty()){
                if(nonExistentGroups.size() == groupIds.size()){
                    throw new RuntimeException("None of the provided groups exist: " + nonExistentGroups);
                }
                else{
                    System.out.println("Warning: The following groups were not found: " + nonExistentGroups);
                }
            }

            FileGroups fileGroups = fileGroupRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found"));

            Set<Long> existingGroups = fileGroups.getGroups();
            existingGroups.addAll(groupIds);
            existingGroups.removeAll(nonExistentGroups);

            fileGroups.setGroups(existingGroups);
            return fileGroupRepository.save(fileGroups);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to add groups: " + e.getMessage());
        }
    }

    public FileGroups restrictAccessFromFile(String fileId, Set<Long> groupIds){
        try{
            Set<Long> nonExistentGroups = new HashSet<>();

            for (Long groupId : groupIds) {
                if (!groupRepository.existsById(groupId)) {
                    nonExistentGroups.add(groupId);
                }
            }

            if (!nonExistentGroups.isEmpty()) {
                if (nonExistentGroups.size() == groupIds.size()) {
                    throw new RuntimeException("None of the provided groups exist: " + nonExistentGroups);
                } else {
                    System.out.println("Warning: The following groups were not found: " + nonExistentGroups);
                }
            }

            FileGroups fileGroups = fileGroupRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found"));

            Set<Long> existingGroups = fileGroups.getGroups();
            existingGroups.removeAll(groupIds);

            fileGroups.setGroups(existingGroups);
            return fileGroupRepository.save(fileGroups);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to remove groups: " + e.getMessage());
        }
    }
}
