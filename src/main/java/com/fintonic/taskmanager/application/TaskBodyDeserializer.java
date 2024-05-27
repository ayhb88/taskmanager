package com.fintonic.taskmanager.application;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fintonic.taskmanager.infrastructure.TaskBody;

import java.io.IOException;
import java.time.LocalDate;

public class TaskBodyDeserializer extends StdDeserializer<TaskBody> {

    protected TaskBodyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TaskBody deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String name = node.get("name").asText();
        String desc = node.get("description").asText();
        String date = node.get("dueDate").asText();

        return new TaskBody(name, desc, LocalDate.parse(date));
    }
}
