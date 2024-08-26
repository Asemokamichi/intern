package com.file_manager.services;

import com.file_manager.Topics.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = KafkaTopics.FILE_UPLOAD_TOPIC, groupId = "file-notifications-group")
    public void listenUploadNotifications(String message) {
        System.out.println("Received upload notification: " + message);
    }

    @KafkaListener(topics = KafkaTopics.FILE_DOWNLOAD_TOPIC, groupId = "file-notifications-group")
    public void listenDownloadNotifications(String message) {
        System.out.println("Received download notification: " + message);
    }

    @KafkaListener(topics = KafkaTopics.FILE_DELETE_TOPIC, groupId = "file-notifications-group")
    public void listenDeleteNotifications(String message) {
        System.out.println("Received delete notification: " + message);
    }

    @KafkaListener(topics = KafkaTopics.FILE_UPDATE_TOPIC, groupId = "file-notifications-group")
    public void listenUpdateNotifications(String message) {
        System.out.println("Received update notification: " + message);
    }
}
