package com.fintonic.taskmanager.infrastructure;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public record TaskBody (String name, String description, LocalDate dueDate){

    public TaskBody {

        requireNonNull(name);
        requireNonNull(description);
        requireNonNull(dueDate);
    }

}
