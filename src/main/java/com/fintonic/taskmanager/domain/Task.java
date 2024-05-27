package com.fintonic.taskmanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static com.fintonic.taskmanager.domain.Check.check;
import static com.fintonic.taskmanager.domain.TaskStatus.DONE;
import static com.fintonic.taskmanager.domain.TaskStatus.IN_PROGRESS;
import static java.util.Objects.requireNonNull;

@Document("task")
public record Task(
        @Id
        UUID idTask,
        TaskName name,
        TaskDescription description,
        LocalDate dueDate,
        Instant createdAt,
        Instant updatedDate,
        TaskStatus status
) {

    public Task {
        requireNonNull(idTask);
        requireNonNull(name);
        requireNonNull(description);
        requireNonNull(dueDate);
        requireNonNull(createdAt);
        requireNonNull(updatedDate);
        requireNonNull(status);
    }

    public Task start() {
        check(status != DONE, "Can't start the task because it's done");
        if (status == IN_PROGRESS) {
            return this;
        }
        return new Task(idTask, name, description, dueDate, createdAt, updatedDate, IN_PROGRESS);
    }

    public Task finish() {
        if (status == DONE) {
            return this;
        }
        return new Task(idTask, name, description, dueDate, createdAt, updatedDate, DONE);
    }
}
