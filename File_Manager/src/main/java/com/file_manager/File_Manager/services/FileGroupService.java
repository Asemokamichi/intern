package com.file_manager.File_Manager.services;

import com.file_manager.File_Manager.dto.FileGroups;
import com.file_manager.File_Manager.repositories.FileGroupRepository;
import com.file_manager.File_Manager.repositories.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FileGroupService {

    @Autowired
    private final FileGroupRepository fileGroupRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void giveAccessToFile(String fileId, List<Long> groupIds){
        try {
            FileGroups fileGroups = fileGroupRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            List<Long> existingGroups = new ArrayList<>(fileGroups.getGroups());

            for (Long groupId : groupIds) {
                if (!existingGroups.contains(groupId)) {
                    existingGroups.add(groupId);
                }
            }
            fileGroups.setGroups(existingGroups);
            fileGroupRepository.save(fileGroups);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to give access to file: " + e.getMessage());
        }
    }

    public void restrictAccessToFile(String fileId, List<Long> groupIds){
        try{
            FileGroups fileGroups = fileGroupRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found"));

            List<Long> existingGroups = new ArrayList<>(fileGroups.getGroups());
            existingGroups.removeAll(groupIds);

            fileGroups.setGroups(existingGroups);
            fileGroupRepository.save(fileGroups);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to restrict access to file: " + e.getMessage());
        }
    }

    public List<Long> getGroupsWithAccess(String fileId) {
        try {
            FileGroups fileGroups = fileGroupRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found"));

            return fileGroups.getGroups();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve groups with access: " + e.getMessage());
        }
    }
}
