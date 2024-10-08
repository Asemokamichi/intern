package com.file_manager.controllers;


import com.file_manager.Topics.KafkaTopics;
import com.file_manager.enums.Permission;
import com.file_manager.services.FileGroupService;
import com.file_manager.services.GroupService;
import com.file_manager.services.KafkaProducerService;
import com.file_manager.services.MinioService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final MinioService minioService;
    private final KafkaProducerService kafkaProducerService;
    private final GroupService groupService;
    private final FileGroupService fileGroupService;

    public FileController(MinioService minioService, KafkaProducerService kafkaProducerService,
                          GroupService groupService, FileGroupService fileGroupService) {
        this.minioService = minioService;
        this.kafkaProducerService = kafkaProducerService;
        this.groupService = groupService;
        this.fileGroupService = fileGroupService;
    }


    private static final List<String> SUPPORTED_CONTENT_TYPES = Arrays.asList(
            "application/msword",           // doc
            "application/vnd.ms-excel",     // xls
            "application/vnd.oasis.opendocument.text",  // odt
            "application/vnd.oasis.opendocument.spreadsheet", // ods
            "application/pdf",              // pdf
            "image/jpeg",                   // jpg/jpeg
            "image/png",                    // png
            "image/gif",                    // gif
            "video/mp4",                    // mp4
            "audio/mpeg",                    // mp3
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"  //docx
    );

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Long groupId){
        try{
            checkPermission(groupId, Permission.WRITE);
            String bucketName = "user1";
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();

            if (!SUPPORTED_CONTENT_TYPES.contains(contentType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file type.");
            }

            String uniqueID = UUID.randomUUID().toString();
            String fileName = uniqueID + originalFilename;

            minioService.uploadFile(bucketName, fileName, file.getBytes(), contentType);

            fileGroupService.giveAccessToFile(uniqueID, Set.of(groupId));

            String message = "File " + originalFilename + " has been uploaded with ID: " + uniqueID;
            kafkaProducerService.sendNotification(KafkaTopics.FILE_UPLOAD_TOPIC, message);

            return ResponseEntity.ok("File uploaded successfully with ID: " + uniqueID);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File upload failed due to server error.");
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFileById(@PathVariable String id, Long groupId) {
        try {

            checkPermission(groupId, Permission.READ);

            String bucketName = "user1";

            String fileName = minioService.findFileNameById(bucketName, id);

            if (fileName == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            InputStream fileStream = minioService.downloadFile(bucketName, fileName);
            byte[] content = fileStream.readAllBytes();

            String contentType = URLConnection.guessContentTypeFromName(fileName);

            String message = "File with ID: " + id + " has been downloaded.";
            kafkaProducerService.sendNotification(KafkaTopics.FILE_DOWNLOAD_TOPIC, message);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(content);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFile(@PathVariable String id, @RequestParam("file") MultipartFile newFile, Long groupId){
        try{

            checkPermission(groupId, Permission.WRITE);

            String bucketName = "user1";
            String contentType = newFile.getContentType();

            if (!SUPPORTED_CONTENT_TYPES.contains(contentType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file type.");
            }

            String newFileName = id + newFile.getOriginalFilename();

            minioService.deleteFile(bucketName, id);
            minioService.uploadFile(bucketName, newFileName, newFile.getBytes(), newFile.getContentType());

            String message = "File with ID: " + id + " has been updated.";
            kafkaProducerService.sendNotification(KafkaTopics.FILE_UPDATE_TOPIC, message);

            return ResponseEntity.ok("File with ID: " + id + " has been updated");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("File update failed due to server error.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String id, Long groupId){
        try{
            checkPermission(groupId, Permission.DELETE);

            String bucketName = "user1";
            minioService.deleteFile(bucketName, id);

            String message = "File with ID: " + id + " has been deleted.";
            kafkaProducerService.sendNotification(KafkaTopics.FILE_DELETE_TOPIC, message);

            return ResponseEntity.ok("File deleted successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("File deletion failed");
        }
    }

    private void checkPermission(Long groupId, Permission requiredPermission){
        List<Permission> permissions = groupService.getPermissionsForGroup(groupId);

        if (!permissions.contains(requiredPermission)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");
        }
    }
}
