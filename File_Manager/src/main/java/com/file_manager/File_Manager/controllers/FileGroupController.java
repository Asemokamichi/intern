package com.file_manager.File_Manager.controllers;

import com.file_manager.File_Manager.dto.FileGroups;
import com.file_manager.File_Manager.repositories.FileGroupRepository;
import com.file_manager.File_Manager.services.FileGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/files/groups")
public class FileGroupController {

    private final FileGroupService fileGroupService;

    @PostMapping("/giveAccess")
    public ResponseEntity<FileGroups> giveAccessToFile(@RequestParam String fileId, @RequestBody Set<Long> groupIds){
        FileGroups fileGroups = fileGroupService.giveAccessToFile(fileId, groupIds);
        return ResponseEntity.ok(fileGroups);
    }

    @PostMapping("/restrictAccess")
    public ResponseEntity<FileGroups> restrictAccessFromFile(@RequestParam String fileId, @RequestBody Set<Long> groupIds){
        FileGroups fileGroups = fileGroupService.restrictAccessFromFile(fileId, groupIds);
        return ResponseEntity.ok(fileGroups);
    }
}
