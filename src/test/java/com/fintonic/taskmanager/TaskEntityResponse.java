package com.fintonic.taskmanager;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TaskEntityResponse(
        String idTask,
        String name,
        String description,
        String dueDate,
        String createdAt,
        String updatedDate,
        String status
) {
}
