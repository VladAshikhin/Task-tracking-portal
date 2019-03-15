package com.task.tracking.service.controller;

import com.task.tracking.service.Utils.TaskUtils;
import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.domain.entity.Task;
import com.task.tracking.service.mapper.UserMapper;
import com.task.tracking.service.mapper.TaskMapper;
import com.task.tracking.service.service.UserService;
import com.task.tracking.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    private TaskService taskService;
    private TaskMapper taskMapper;
    private TaskUtils taskUtils;
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public void setTaskService(TaskService taskService, TaskMapper taskMapper,
                               TaskUtils taskUtils, UserService userService,
                               UserMapper userMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskUtils = taskUtils;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RequestMapping(value="/")
    public String home() {
        return "home";
    }

    @RequestMapping(value="/user")
    public String user() {
        return "user";
    }

    @RequestMapping(value="/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value="/403")
    public String Error403() {
        return "403";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        List<TaskDto> tasks = taskService.getAll();

        model.addAttribute("tasks", tasks);
        model.addAttribute("taskToSave", new Task());

        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("taskToSave") TaskDto task) {
        task.setCreatedBy(taskUtils.getLoggedInUserId());

        taskService.save(task);
        return "redirect:tasks";
    }
}