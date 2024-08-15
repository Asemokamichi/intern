package com.notification_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDto {
    private Long objectId; //new user or department
    private List<Long> recipientIds; //all users or department users.
}
