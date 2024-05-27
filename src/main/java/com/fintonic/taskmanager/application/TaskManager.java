package com.fintonic.taskmanager.application;

import com.fintonic.taskmanager.domain.TaskRepository;
import com.fintonic.taskmanager.domain.Task;
import com.fintonic.taskmanager.domain.TaskDescription;
import com.fintonic.taskmanager.domain.TaskName;
import com.fintonic.taskmanager.infrastructure.TaskBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fintonic.taskmanager.domain.TaskStatus.TO_DO;

@Service
public class TaskManager {

    @Autowired
    TaskRepository taskRepository;

    public Task createTask(TaskBody taskBody) {

        if (taskBody.dueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date can't be before than current date");
        }

        Task task = new Task(UUID.randomUUID(), new TaskName(taskBody.name()), new TaskDescription(taskBody.description()), taskBody.dueDate(), Instant.now(), Instant.now(), TO_DO);
        taskRepository.save(task);
        return task;
    }

    public List<Task> getAll() {
        List<Task> list = new ArrayList<>();
        list = taskRepository.findAll();
        return list;
    }

    public Optional<Task> getById(UUID id) {
        return taskRepository.findById(id);
    }

}
