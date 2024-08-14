package com.task_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task_manager.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "position")
    private String position;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Resolution> resolutions;

    public User(Long id) {
        this.id = id;
    }
}
