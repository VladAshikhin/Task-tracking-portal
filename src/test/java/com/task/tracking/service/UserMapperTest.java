package com.task.tracking.service;

import com.task.tracking.service.domain.dto.UserDto;
import com.task.tracking.service.domain.entity.User;
import com.task.tracking.service.mapper.impl.UserMapperImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    private static final Integer ID = 1;
    private static final Integer ID1 = 2;
    private static final String LOGIN = "root";
    private static final String PASSWORD = "rootp";
    private static final String EMAIL = "root@root.com";
    private static final String ROLE = "ROLE_ADMIN";
    private static final boolean ACTIVE = true;
    private static final Integer TASK_ID = 101;

    UserMapperImpl userMapper = new UserMapperImpl();

    @Test
    public void DtoToEntityTest() {
        assertEquals(prepareUserEntity(ID), userMapper.dtoToEntity(prepareUserDto(ID)));
    }

    @Test
    public void EntityToDtoTest() {
        assertEquals(prepareUserDto(ID1), userMapper.entityToDto(prepareUserEntity(ID1)));
    }

    @Test
    public void EntityListToDtoListTest() {
        List<User> entityList = new ArrayList<>();
        List<UserDto> dtoList = new ArrayList<>();

        entityList.add(prepareUserEntity(ID));
        entityList.add(prepareUserEntity(ID1));
        dtoList.add(prepareUserDto(ID));
        dtoList.add(prepareUserDto(ID1));

        assertEquals(dtoList, userMapper.entityListToDtoList(entityList));
    }

    @Test
    public void DtoListToEntityListTest() {
        List<User> entityList = new ArrayList<>();
        List<UserDto> dtoList = new ArrayList<>();

        entityList.add(prepareUserEntity(ID));
        entityList.add(prepareUserEntity(ID1));
        dtoList.add(prepareUserDto(ID));
        dtoList.add(prepareUserDto(ID1));

        assertEquals(entityList, userMapper.dtoListToEntityList(dtoList));
    }

    private User prepareUserEntity(Integer id) {
        return new User(
                id, LOGIN, PASSWORD, EMAIL, ROLE, ACTIVE, TASK_ID);
    }

    private UserDto prepareUserDto(Integer id) {
        return new UserDto(
                id, LOGIN, PASSWORD, EMAIL, ROLE, ACTIVE, TASK_ID);
    }

}