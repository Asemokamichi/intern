package com.user_manager.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public NewTopic userCreatedTopic() {
        return new NewTopic("user.created", 1, (short) 1);
    }

    @Bean
    public NewTopic userInfoEditedTopic() {
        return new NewTopic("user.info.edited", 1, (short) 1);
    }

    @Bean
    public NewTopic userPositionUpdateTopic() {
        return new NewTopic("user.position.update", 1, (short) 1);
    }

    @Bean
    public NewTopic userRoleUpdateTopic() {
        return new NewTopic("user.role.update", 1, (short) 1);
    }

    @Bean
    public NewTopic userDepartmentUpdateTopic() {
        return new NewTopic("user.department.update", 1, (short) 1);
    }

    @Bean
    public NewTopic userAddedToDepartmentTopic() {
        return new NewTopic("user.added.to.department", 1, (short) 1);
    }

    @Bean
    public NewTopic userActivatedTopic() {
        return new NewTopic("user.activated", 1, (short) 1);
    }

    @Bean
    public NewTopic userDeactivatedTopic() {
        return new NewTopic("user.deactivated", 1, (short) 1);
    }

    @Bean
    public NewTopic userDeletedTopic() {
        return new NewTopic("user.deleted", 1, (short) 1);
    }

    @Bean
    public NewTopic departmentCreatedTopic() {
        return new NewTopic("department.created", 1, (short) 1);
    }

    @Bean
    public NewTopic departmentUpdateTopic() {
        return new NewTopic("department.update", 1, (short) 1);
    }

    @Bean
    public NewTopic departmentDeleteTopic() {
        return new NewTopic("department.delete", 1, (short) 1);
    }

    @Bean
    public NewTopic departmentHeadDeleteTopic() {
        return new NewTopic("department.head.delete", 1, (short) 1);
    }

    @Bean
    public NewTopic departmentParentDeleteTopic() {
        return new NewTopic("department.parent.delete", 1, (short) 1);
    }




}
