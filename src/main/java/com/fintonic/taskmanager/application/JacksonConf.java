package com.fintonic.taskmanager.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConf {

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        return new ObjectMapper().registerModule(new TaskSerialization());

    }
}
