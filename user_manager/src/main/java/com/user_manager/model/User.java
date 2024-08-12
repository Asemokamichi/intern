package com.user_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Transient
    private Map<String, String> contacts;

    @JsonIgnore
    @Column(name = "contacts")
    private String serializedContacts;


    @PrePersist
    @PreUpdate
    private void serializeContacts() throws IOException {
        if (contacts != null) {
            serializedContacts = new ObjectMapper().writeValueAsString(contacts);
        }
    }

    @PostLoad
    private void deserializeContacts() throws IOException {
        if (serializedContacts != null) {
            contacts = new ObjectMapper().readValue(serializedContacts, new TypeReference<Map<String, String>>() {});
        }
    }

    private String position;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String creationDate;
    private String modificationDate;
    private Boolean isActive;

}
