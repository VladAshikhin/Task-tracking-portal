package com.task.tracking.service.service;

import com.task.tracking.service.domain.dto.TaskDto;

import java.util.List;

/**
 * Service is a class of main business-logic.
 * Uses Repositories to represent data to UI.
 */

public interface TaskService {

    void save(TaskDto task);

    List<TaskDto> getAll();

    TaskDto findById(Integer id);

    TaskDto findByUserId(Integer assignedToId);

    List<TaskDto> findTasksBySubject(String subject);

    void update(TaskDto task);

}