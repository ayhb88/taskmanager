package com.fintonic.taskmanager.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends MongoRepository<Task, UUID> {

    @NonNull
    List<Task> findAll();

}
