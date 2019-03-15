package com.task.tracking.service.service.impl;

import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.domain.entity.Task;
import com.task.tracking.service.domain.repository.TaskRepository;
import com.task.tracking.service.mapper.TaskMapper;
import com.task.tracking.service.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;

    TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getAll() {
        List<Task> taskList = taskRepository.findAll();

        List<TaskDto> dtos = taskMapper.entityListToDtoList(taskList);
        return dtos;
    }

    @Override
    public TaskDto findById(Integer id) {
        Task entity = taskRepository.findById(id);
            return taskMapper.entityToDto(entity);
    }

    @Override
    public TaskDto findByUserId(Integer assignedToId) {
        return taskMapper.entityToDto(taskRepository.findByUserId(assignedToId));
    }

    @Override
    public List<TaskDto> findTasksBySubject(String subject) {
        return taskMapper.entityListToDtoList(taskRepository.findTasksBySubject(subject));
    }

    @Override
    public void update(TaskDto task) {
    taskRepository.save(taskMapper.dtoToEntity(task));
    }

    @Override
    public void save(TaskDto task) {
        task.setCreatedOn(LocalDate.now());
        taskRepository.save(taskMapper.dtoToEntity(task));
    }
}