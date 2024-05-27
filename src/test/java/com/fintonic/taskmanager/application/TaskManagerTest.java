package com.fintonic.taskmanager.application;

import static org.junit.jupiter.api.Assertions.*;

import com.fintonic.taskmanager.domain.Task;
import com.fintonic.taskmanager.domain.TaskDescription;
import com.fintonic.taskmanager.domain.TaskName;
import com.fintonic.taskmanager.domain.TaskRepository;
import com.fintonic.taskmanager.infrastructure.TaskBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fintonic.taskmanager.domain.TaskStatus.TO_DO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskManagerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnTask_WhenValidTaskBody() {
        TaskBody taskBody = new TaskBody("Test Task", "Description", LocalDate.now().plusDays(1));
        Task task = new Task(UUID.randomUUID(), new TaskName(taskBody.name()), new TaskDescription(taskBody.description()), taskBody.dueDate(), Instant.now(), Instant.now(), TO_DO);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskManager.createTask(taskBody);

        assertNotNull(createdTask);
        assertEquals(taskBody.name(), createdTask.name().name());
        assertEquals(taskBody.description(), createdTask.description().description());
        assertEquals(taskBody.dueDate(), createdTask.dueDate());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void createTask_ShouldThrowException_WhenDueDateIsBeforeCurrentDate() {
        TaskBody taskBody = new TaskBody("Test Task", "Description", LocalDate.now().minusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask(taskBody);
        });

        assertEquals("Due date can't be before than current date", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getAll_ShouldReturnAllTasks() {
        List<Task> tasks = Arrays.asList(
                new Task(UUID.randomUUID(), new TaskName("Task1"), new TaskDescription("Desc1"), LocalDate.now().plusDays(1), Instant.now(), Instant.now(), TO_DO),
                new Task(UUID.randomUUID(), new TaskName("Task2"), new TaskDescription("Desc2"), LocalDate.now().plusDays(2), Instant.now(), Instant.now(), TO_DO)
        );

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> allTasks = taskManager.getAll();

        assertNotNull(allTasks);
        assertEquals(2, allTasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnTask_WhenTaskExists() {
        UUID id = UUID.randomUUID();
        Task task = new Task(id, new TaskName("Task"), new TaskDescription("Description"), LocalDate.now().plusDays(1), Instant.now(), Instant.now(), TO_DO);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskManager.getById(id);

        assertTrue(foundTask.isPresent());
        assertEquals(task, foundTask.get());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void getById_ShouldReturnEmpty_WhenTaskDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Task> foundTask = taskManager.getById(id);

        assertFalse(foundTask.isPresent());
        verify(taskRepository, times(1)).findById(id);
    }
}