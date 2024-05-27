package com.fintonic.taskmanager.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskNameTest {

    @DisplayName("A name shouldn't be largest than fifty characters")
    @Test
    void aNameShouldntBeLargestThanFiftyCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
                new TaskName("task_0000000000000000000000000000000000000000000001")
        );
    }
}