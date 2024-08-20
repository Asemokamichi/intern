package com.user_manager.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_manager.dto.NotificationDto;
import com.user_manager.enums.UserNotificationTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendNotification(NotificationDto notificationDto, UserNotificationTopic topic) {
        try{
            String json = objectMapper.writeValueAsString(notificationDto);
            kafkaTemplate.send(topic.getTopicName(), json);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
