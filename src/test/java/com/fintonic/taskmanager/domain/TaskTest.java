package com.fintonic.taskmanager.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static com.fintonic.taskmanager.domain.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @DisplayName("A task in status ToDO should change to in progress")
    @Test
    void aTaskInStatusToDO_ShouldChangeToInProgress() {
        Task task = new Task(UUID.randomUUID(), new TaskName("task1"), new TaskDescription("description1"), LocalDate.now(), Instant.now(), Instant.now(), TO_DO);
        var result = task.start();
        assertEquals(IN_PROGRESS, result.status());
    }

    @DisplayName("A task in status in progress should be able to change to done")
    @Test
    void aTaskInStatusStart_ShouldBeAbleToChangeToFinish() {
        Task task = new Task(UUID.randomUUID(), new TaskName("task1"), new TaskDescription("description1"), LocalDate.now(), Instant.now(), Instant.now(), IN_PROGRESS);
        var result = task.finish();
        assertEquals(DONE, result.status());
    }

    @DisplayName("A task in status done shouldn't be able to change to in progress")
    @Test
    void aTaskInStatusFinish_ShouldntBeAbleToChangeToStart() {
        Task task = new Task(UUID.randomUUID(), new TaskName("task1"), new TaskDescription("description1"), LocalDate.now(), Instant.now(), Instant.now(), DONE);
        assertThrows(IllegalArgumentException.class, task::start);
    }

}