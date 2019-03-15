package com.task.tracking.service.service.impl;

import com.task.tracking.service.domain.dto.UserDto;
import com.task.tracking.service.domain.entity.User;
import com.task.tracking.service.domain.repository.UserRepository;
import com.task.tracking.service.mapper.UserMapper;
import com.task.tracking.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> taskList = userRepository.findAll();
        List<UserDto> dtos = userMapper.entityListToDtoList(taskList);
        return dtos;
    }

    @Override
    public UserDto findById(Integer id) {
        User entity = userRepository.findById(id);
        return userMapper.entityToDto(entity);
    }

    @Override
    public UserDto findByLogin(String login) {
        User entity = userRepository.getUserByLogin(login);
        return userMapper.entityToDto(entity);
    }

    @Override
    public List<UserDto> findByRole(String role) {
        List<User> entities = userRepository.findByRole(role);
        return userMapper.entityListToDtoList(entities);
    }

    @Override
    public List<UserDto> findActive() {
        List<User> entities = userRepository.findActive();
        return userMapper.entityListToDtoList(entities);
    }

    @Override
    public Integer getNumberOfRegisteredUsers() {
        return userRepository.getNumberOfRegisteredUsers();
    }
}