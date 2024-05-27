package com.fintonic.taskmanager.infrastructure;

import com.fintonic.taskmanager.application.TaskManager;
import com.fintonic.taskmanager.domain.Task;
import com.fintonic.taskmanager.domain.TaskDescription;
import com.fintonic.taskmanager.domain.TaskName;
import com.fintonic.taskmanager.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.fintonic.taskmanager.domain.TaskStatus.TO_DO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnCreatedTask_WhenValidTaskBody() throws Exception {
        TaskBody taskBody = new TaskBody("Test Task", "Description", LocalDate.now().plusDays(1));
        Task task = new Task(UUID.randomUUID(), new TaskName(taskBody.name()), new TaskDescription(taskBody.description()), taskBody.dueDate(), Instant.now(), Instant.now(), TO_DO);
        when(taskManager.createTask(any(TaskBody.class))).thenReturn(task);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Task\", \"description\":\"Description\", \"dueDate\":\"" + LocalDate.now().plusDays(1) + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(taskBody.name()))
                .andExpect(jsonPath("$.description").value(taskBody.description()))
                .andExpect(jsonPath("$.dueDate").value(taskBody.dueDate().toString()));
    }

    @Test
    void createTask_ShouldReturnBadRequest_WhenDueDateIsBeforeCurrentDate() throws Exception {
        TaskBody taskBody = new TaskBody("Test Task", "Description", LocalDate.now().minusDays(1));

        when(taskManager.createTask(any(TaskBody.class))).thenThrow(new IllegalArgumentException("Due date can't be before than current date"));

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Task\", \"description\":\"Description\", \"dueDate\":\"" + LocalDate.now().minusDays(1) + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_ShouldReturnAllTasks_WhenTasksExist() throws Exception {
        Task task1 = new Task(UUID.randomUUID(), new TaskName("Task1"), new TaskDescription("Desc1"), LocalDate.now().plusDays(1), Instant.now(), Instant.now(), TO_DO);
        Task task2 = new Task(UUID.randomUUID(), new TaskName("Task2"), new TaskDescription("Desc2"), LocalDate.now().plusDays(2), Instant.now(), Instant.now(), TO_DO);

        when(taskManager.getAll()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Task1"))
                .andExpect(jsonPath("$[1].name").value("Task2"));
    }

    @Test
    void getAll_ShouldReturnNoContent_WhenNoTasksExist() throws Exception {
        when(taskManager.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getById_ShouldReturnTask_WhenTaskExists() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task(id, new TaskName("Task"), new TaskDescription("Description"), LocalDate.now().plusDays(1), Instant.now(), Instant.now(), TO_DO);

        when(taskManager.getById(id)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/task/" + id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Task"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void getById_ShouldReturnNotFound_WhenTaskDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        when(taskManager.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/task/" + id.toString()))
                .andExpect(status().isNotFound());
    }
}