package com.task.tracking.service;

import com.task.tracking.service.domain.dto.TaskDto;
import com.task.tracking.service.service.TaskService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationContext.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskControllerTest {

    private static final Integer ID = 101;
    private static final Integer ID1 = 102;
    private static final String SUBJECT = "TestSubject";
    private static final String MESSAGE = "TestMessage";
    private static final Integer PRIORITY = 30;
    private static final Integer CREATED_BY = 10;
    private static final LocalDate CREATED_ON = LocalDate.of(2019, 01, 01);
    private static final Integer USER_ID = 10;
    private static final boolean TASK_ACTIVE = true;
    private static final String CLOSE_MESSAGE = "Task done in time";
    private static final Integer LOGGED_IN_USER = 1;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @SneakyThrows(Exception.class)
    public void GetAllTasksTest() {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tasks"))
                .andExpect(view().name("tasks"));
    }

    @Test
    @SneakyThrows(Exception.class)
    public void testHome() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @SneakyThrows(Exception.class)
    public void testUser() {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"));
    }

    @Test
    @SneakyThrows(Exception.class)
    public void testAdmin() {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @SneakyThrows(Exception.class)
    public void testLogin() {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @SneakyThrows(Exception.class)
    public void testError() {
        mockMvc.perform(get("/403"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"));
    }
}