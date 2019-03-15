package com.task.tracking.service.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class that represents the Task entity where:
 * createdOn - date of creation, by default 'current_date';
 * userId - id of the user, who assigns a task to himself;
 * closeMessage - message to be specified while closing a task;
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    //@Basic(optional = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn = LocalDate.now();

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "task_active")
    private boolean taskActive = true;

    @Column(name = "close_message")
    private String closeMessage;
}