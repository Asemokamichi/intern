package com.task_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task_manager.dto.CreateTaskDto;
import com.task_manager.enums.Status;
import com.task_manager.enums.TypeTask;
import com.task_manager.util.LongArrayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "target_date")
    private LocalDateTime targetDate;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "type_task")
    @Enumerated(EnumType.STRING)
    private TypeTask typeTask;

    @Column(name = "is_parallel")
    private Boolean isParallel;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "responsibles", columnDefinition = "TEXT", length = Integer.MAX_VALUE)
    @Convert(converter = LongArrayConverter.class)
    private Long[] responsibles;

    @JsonIgnore
    @OneToMany(mappedBy = "task")
    private List<Resolution> resolutions;

    @JsonIgnore
    @OneToMany(mappedBy = "task")
    private List<Notification> notifications;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    public Task(CreateTaskDto taskDto) {
        if (taskDto.getCreationDate() != null) creationDate = taskDto.getCreationDate();
        else creationDate = LocalDateTime.now();
        targetDate = taskDto.getTargetDate();
        typeTask = TypeTask.valueOf(taskDto.getType());
        isParallel = taskDto.getIsParallel();
        responsibles = taskDto.getResponsibles();
        title = taskDto.getTitle();
        body = taskDto.getBody();
    }

    public Task(Long id) {
        this.id = id;
    }
}
