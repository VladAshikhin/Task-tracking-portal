package com.task.tracking.service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class that represents the User entity as a user where:
 * role - role of the user; specified in format 'ROLE_USER' or 'ROLE_ADMIN';
 * active - by default = true;
 * taskId - id of a task that is assigned to the user;
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "task_id")
    private Integer taskId;
}