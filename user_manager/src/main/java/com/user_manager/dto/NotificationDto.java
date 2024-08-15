package com.user_manager.dto;

import com.user_manager.model.Department;
import com.user_manager.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long objectId;

    private List<Long> recipientIds; //all users or department users.


    public NotificationDto(User user, List<Long> recipientIds) {
        this.objectId = user.getId();
        this.recipientIds = recipientIds;
    }

    public NotificationDto(Department department, List<Long> recipientIds) {
        this.objectId = department.getId();
        this.recipientIds = recipientIds;

    }
}
