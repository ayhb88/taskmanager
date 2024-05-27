package com.fintonic.taskmanager.application;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fintonic.taskmanager.domain.Task;
import com.fintonic.taskmanager.infrastructure.TaskBody;

public class TaskSerialization extends SimpleModule {

    TaskSerialization(){
        addSerializer(Task.class, new TaskSerializer(Task.class));
        addDeserializer(TaskBody.class, new TaskBodyDeserializer(TaskBody.class));
    }
}
