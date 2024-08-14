package com.user_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String creationDate;
    private String modificationDate;

    @ManyToOne
    @JoinColumn(name = "parent_department_id")
    @JsonBackReference
    private Department parentDepartment;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "parentDepartment", cascade = CascadeType.ALL)
    private List<Department> childDepartments = new ArrayList<>();


}
