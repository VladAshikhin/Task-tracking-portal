package com.task.tracking.service.controller;

import com.task.tracking.service.Utils.TaskUtils;
import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.mapper.UserMapper;
import com.task.tracking.service.mapper.TaskMapper;
import com.task.tracking.service.service.UserService;
import com.task.tracking.service.service.TaskService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private TaskService taskService;
    private TaskMapper taskMapper;
    private TaskUtils taskUtils;
    UserService userService;
    UserMapper userMapper;

    @Autowired
    public RestController(TaskService taskService, TaskMapper taskMapper, TaskUtils taskUtils,
                          UserService userService, UserMapper userMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskUtils = taskUtils;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Controller for assigning a task to the logged in user
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/tasks/assign", method = RequestMethod.POST)
    public Integer assignTask(@RequestBody TaskDto task) {
        TaskDto taskToUpdate = taskService.findById(task.getId());
        Integer userId = taskUtils.getLoggedInUserId();
        taskToUpdate.setUserId(userId);

        taskService.update(taskToUpdate);
        return userId;
    }

    /**
     * Controller for completing a task
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/tasks/close", method = RequestMethod.POST)
    public String closeTask(@RequestBody TaskDto task) {
        TaskDto taskToUpdate = taskService.findById(task.getId());
        String message = task.getCloseMessage();

        taskToUpdate.setCloseMessage(message);
        taskToUpdate.setTaskActive(false);

        taskService.update(taskToUpdate);
        return "{}"; //String
    }

    /**
     * Controller for getting a number of users registered in db.migration
     * @return number of users
     */
    @ResponseBody
    @GetMapping("/users/number")
    public String getNumberOfUsers() {
        return userService.getNumberOfRegisteredUsers().toString();
    }

    /**
     * Controller for getting loggedIn user ID
     * @return userId
     */
    @ResponseBody
    @GetMapping("/users/id")
    public String getLoggedInUserId() {
        return taskUtils.getLoggedInUserId().toString();
    }
}