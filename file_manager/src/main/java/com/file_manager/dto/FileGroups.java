package com.file_manager.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "file_groups")
public class FileGroups {

    @Id
    @Column(name = "file_id", nullable = false)
    private String fileID;

    @ElementCollection
    @CollectionTable(name = "file_groups_map", joinColumns = @JoinColumn(name = "file_id"))
    @Column(name = "group_id")
    private Set<Long> groups;

}
