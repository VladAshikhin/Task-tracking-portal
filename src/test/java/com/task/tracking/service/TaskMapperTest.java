package com.task.tracking.service;

import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.domain.entity.Task;
import com.task.tracking.service.mapper.impl.TaskMapperImpl;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskMapperTest {

    private static final Integer ID = 101;
    private static final Integer ID1 = 102;
    private static final String SUBJECT = "Subject";
    private static final String MESSAGE = "Message";
    private static final Integer PRIORITY = 30;
    private static final Integer CREATED_BY = 10;
    private static final LocalDate CREATED_ON = LocalDate.of(2019, 01, 01);
    private static final Integer USER_ID = 10;
    private static final boolean TASK_ACTIVE = true;
    private static final String CLOSE_MESSAGE = "Done in time";


    TaskMapperImpl taskMapper = new TaskMapperImpl();

    @Test
    public void DtoToEntityTest() {
        assertEquals(prepareTaskEntity(ID), taskMapper.dtoToEntity(prepareTaskDto(ID)));
    }

    @Test
    public void EntityToDtoTest() {
        assertEquals(prepareTaskDto(ID1), taskMapper.entityToDto(prepareTaskEntity(ID1)));
    }

    @Test
    public void EntityListToDtoListTest() {
        List<Task> entityList = new ArrayList<>();
        List<TaskDto> dtoList = new ArrayList<>();

        entityList.add(prepareTaskEntity(ID));
        entityList.add(prepareTaskEntity(ID1));
        dtoList.add(prepareTaskDto(ID));
        dtoList.add(prepareTaskDto(ID1));

        assertEquals(dtoList, taskMapper.entityListToDtoList(entityList));
    }

    @Test
    public void DtoListToEntityListTest() {
        List<Task> entityList = new ArrayList<>();
        List<TaskDto> dtoList = new ArrayList<>();

        entityList.add(prepareTaskEntity(ID));
        entityList.add(prepareTaskEntity(ID1));
        dtoList.add(prepareTaskDto(ID));
        dtoList.add(prepareTaskDto(ID1));

        assertEquals(entityList, taskMapper.dtoListToEntityList(dtoList));
    }

    private Task prepareTaskEntity(Integer id) {
        return new Task(
                id, SUBJECT, MESSAGE, PRIORITY, CREATED_BY, CREATED_ON, USER_ID, TASK_ACTIVE, CLOSE_MESSAGE);
    }

    private TaskDto prepareTaskDto(Integer id) {
        return new TaskDto(
                id, SUBJECT, MESSAGE, PRIORITY, CREATED_BY, CREATED_ON, USER_ID, TASK_ACTIVE, CLOSE_MESSAGE);
    }

}
