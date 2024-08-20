package com.task_manager.entity;

import com.task_manager.dto.AddResolutionDto;
import com.task_manager.enums.ResolutionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "resolutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resolution {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "text")
    private String text;

    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ResolutionStatus status;

    public Resolution(AddResolutionDto commentDto) {
        creationDate = commentDto.getCreationDate();
        authorId = commentDto.getAuthorID();
        text = commentDto.getText();
    }
}
