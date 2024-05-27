package com.fintonic.taskmanager.infrastructure;

import com.fintonic.taskmanager.application.TaskManager;
import com.fintonic.taskmanager.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController

public class TaskController {

    @Autowired
    TaskManager manager;

    @PostMapping("/")
    public ResponseEntity<Task> createTask(@RequestBody TaskBody taskBody) {
        try {
            Task task = manager.createTask(taskBody);
            return new ResponseEntity<>(task, CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAll() {
        try {
            List<Task> listTasks = manager.getAll();

            if (listTasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listTasks, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getByName(@PathVariable("id")  String id) {
        try {
            Optional<Task> task = manager.getById(UUID.fromString(id));

            return task.map(value -> new ResponseEntity<>(value, OK)).orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
