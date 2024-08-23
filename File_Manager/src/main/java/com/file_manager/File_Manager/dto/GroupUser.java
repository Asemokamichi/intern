package com.file_manager.File_Manager.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "group_users")
public class GroupUser {

    @Id
    private Long groupID;

    @ElementCollection
    @Column(name = "user_id")
    private List<Long> users;
}
