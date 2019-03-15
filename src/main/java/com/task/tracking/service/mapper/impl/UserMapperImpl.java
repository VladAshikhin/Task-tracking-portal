package com.task.tracking.service.mapper.impl;

import com.task.tracking.service.domain.dto.UserDto;
import com.task.tracking.service.domain.entity.User;
import com.task.tracking.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class maps entities to dtos and vice versa
 */
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .taskId(user.getTaskId())
                .build();
    }

    @Override
    public User dtoToEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(dto.getRole())
                .active(dto.isActive())
                .taskId(dto.getTaskId())
                .build();
    }

    @Override
    public List<UserDto> entityListToDtoList(List<User> userList) {
        return userList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> dtoListToEntityList(List<UserDto> userDtos) {
        return userDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}