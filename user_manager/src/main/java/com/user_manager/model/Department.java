package com.user_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Long headId;
    private Long parentDepartmentId;
    private String creationDate;
    private String modificationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<User> users = new ArrayList<>();

}
