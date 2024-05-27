package com.fintonic.taskmanager.domain;

import static com.fintonic.taskmanager.domain.Check.check;
import static java.util.Objects.requireNonNull;

public record TaskName(String name) {
    public TaskName {
        requireNonNull(name);
        check(!name.isBlank(), "Name can't be empty");
        check(name.length() <= 50, "Name can't be larger than 50 characters");
    }
}
