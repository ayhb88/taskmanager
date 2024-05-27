package com.fintonic.taskmanager;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public record TaskEntityRequest(String name, String description, String dueDate) {

}
