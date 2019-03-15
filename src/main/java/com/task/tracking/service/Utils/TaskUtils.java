package com.task.tracking.service.Utils;

import com.task.tracking.service.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
public class TaskUtils {

    UserRepository userRepository;

    @Autowired
    public TaskUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer getLoggedInUserId() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getUserByLogin(loggedInUser.getUsername()).getId();
    }
}