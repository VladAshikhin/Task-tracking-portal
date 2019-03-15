package com.task.tracking.service.domain.repository;

import com.task.tracking.service.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t ORDER BY priority ASC")
    List<Task> findAll();

    Task findById(Integer id);

    Task findByUserId(Integer assignedToId);

    List<Task> findTasksBySubject(String subject);
}