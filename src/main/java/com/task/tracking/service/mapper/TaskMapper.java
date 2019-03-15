package com.task.tracking.service.mapper;

import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.domain.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Interface maps entities to dtos and vice versa
 */
@Component
public interface TaskMapper {

    TaskDto entityToDto(Task task);

    Task dtoToEntity(TaskDto dto);

    List<TaskDto> entityListToDtoList(List<Task> taskList);

    List<Task> dtoListToEntityList(List<TaskDto> taskDtos);

}
