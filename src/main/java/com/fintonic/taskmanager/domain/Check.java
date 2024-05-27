package com.fintonic.taskmanager.domain;

import static java.util.Objects.requireNonNull;

public class Check {

    public Check() {
        throw new RuntimeException("Shouldn't be initialized");
    }

    public static void check(boolean condition, String errorMsg) {
        if (!condition) {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
