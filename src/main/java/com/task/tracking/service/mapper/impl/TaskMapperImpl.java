package com.task.tracking.service.mapper.impl;

import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.domain.entity.Task;
import com.task.tracking.service.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class maps entities to dtos and vice versa
 */
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto entityToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .subject(task.getSubject())
                .message(task.getMessage())
                .priority(task.getPriority())
                .createdBy(task.getCreatedBy())
                .createdOn(task.getCreatedOn())
                .userId(task.getUserId())
                .taskActive(task.isTaskActive())
                .closeMessage(task.getCloseMessage())
                .build();
    }

    @Override
    public Task dtoToEntity(TaskDto dto) {
        return Task.builder()
                .id(dto.getId())
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .priority(dto.getPriority())
                .createdBy(dto.getCreatedBy())
                .createdOn(dto.getCreatedOn())
                .userId(dto.getUserId())
                .taskActive(dto.isTaskActive())
                .closeMessage(dto.getCloseMessage())
                .build();
    }

    @Override
    public List<TaskDto> entityListToDtoList(List<Task> taskList) {
        return taskList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> dtoListToEntityList(List<TaskDto> taskDtos) {
        return taskDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}