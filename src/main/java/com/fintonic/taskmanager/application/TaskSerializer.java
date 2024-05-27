package com.fintonic.taskmanager.application;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fintonic.taskmanager.domain.Task;
import com.fintonic.taskmanager.domain.TaskDescription;
import com.fintonic.taskmanager.domain.TaskName;
import com.fintonic.taskmanager.domain.TaskStatus;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class TaskSerializer extends StdSerializer<Task> {

    protected TaskSerializer(Class<Task> t) {
        super(t);
    }

    @Override
    public void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("idTask", task.idTask().toString());
        jsonGenerator.writeStringField("name", task.name().name());
        jsonGenerator.writeStringField("description", task.description().description());
        jsonGenerator.writeStringField("dueDate", task.dueDate().toString());
        jsonGenerator.writeStringField("createdAt", task.createdAt().toString());
        jsonGenerator.writeStringField("updatedDate", task.updatedDate().toString());
        jsonGenerator.writeStringField("status", task.status().toString());
        jsonGenerator.writeEndObject();
    }
}
