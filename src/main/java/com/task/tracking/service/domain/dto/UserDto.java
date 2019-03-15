package com.task.tracking.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String login;

    private String password;

    private String email;

    private String role;

    private boolean active = true;

    private Integer taskId;
}