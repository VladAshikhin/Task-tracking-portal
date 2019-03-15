package com.task.tracking.service.service;

import com.task.tracking.service.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto findById(Integer id);

    UserDto findByLogin(String login);

    List<UserDto> findByRole(String role);

    List<UserDto> findActive();

    Integer getNumberOfRegisteredUsers();

}
