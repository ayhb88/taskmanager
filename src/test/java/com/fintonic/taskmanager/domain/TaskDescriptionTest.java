package com.fintonic.taskmanager.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskDescriptionTest {

    @DisplayName("A description shouldn't be largest than two hundred fifty characters")
    @Test
    void aDescriptionShouldntBeLargestThanTwoHundredFiftyCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
                new TaskDescription("description_00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001")
        );
    }
}