package com.fintonic.taskmanager.domain;

import static java.util.Objects.requireNonNull;
import static com.fintonic.taskmanager.domain.Check.check;

public record TaskDescription(String description) {
    public TaskDescription {
        requireNonNull(description);
        check(!description.isBlank(), "Description can't be empty");
        check(description.length() <= 250, "Description can't be larger than 250 characters");

    }
}
