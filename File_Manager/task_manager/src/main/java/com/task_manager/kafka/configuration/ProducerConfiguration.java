package com.task_manager.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ProducerConfiguration {
    @Bean
    public NewTopic consumerOffsetsTopic() {
        return new NewTopic("__consumer_offsets", 50, (short) 1)
                .configs(Map.of(
                        "compression.type", "producer",
                        "cleanup.policy", "compact",
                        "segment.bytes", "104857600"
                ));
    }

    @Bean
    public NewTopic taskAssignedTopic() {
        return new NewTopic("task.assigned", 1, (short) 1);
    }

    @Bean
    public NewTopic taskCommentAddedTopic() {
        return new NewTopic("task.comment.added", 1, (short) 1);
    }

    @Bean
    public NewTopic taskCommentClosedTopic() {
        return new NewTopic("task.comment.closed", 1, (short) 1);
    }

    @Bean
    public NewTopic taskCompletedTopic() {
        return new NewTopic("task.completed", 1, (short) 1);
    }

    @Bean
    public NewTopic taskAcceptedStartedTopic() {
        return new NewTopic("task.accepted.started", 1, (short) 1);
    }

    @Bean
    public NewTopic taskDeadlineExtendedTopic() {
        return new NewTopic("task.deadline.extended", 1, (short) 1);
    }

    @Bean
    public NewTopic taskResolutionReceivedTopic() {
        return new NewTopic("task.resolution.received", 1, (short) 1);
    }

    @Bean
    public NewTopic taskResolutionApprovedTopic() {
        return new NewTopic("task.resolution.approved", 1, (short) 1);
    }

    @Bean
    public NewTopic taskResolutionRevisionTopic() {
        return new NewTopic("task.resolution.revision", 1, (short) 1);
    }

    @Bean
    public NewTopic taskDeletedTopic() {
        return new NewTopic("task.deleted", 1, (short) 1);
    }

}