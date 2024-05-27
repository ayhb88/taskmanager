package com.fintonic.taskmanager;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
class TaskManagerApplicationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl());
    }

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Description("WHEN the task creation is executed THEN it should return a two hundred http response AND it should save it in database")
    @Test
    void createATask200() {

        String url = "http://localhost:" + port + "/";
        String name = "task1";
        String desc = "desc1";
        String date = "2024-12-01";

        TaskEntityRequest request = new TaskEntityRequest(name, desc, date);
        ResponseEntity<TaskEntityResponse> response = testRestTemplate.postForEntity(url, request, TaskEntityResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(name, response.getBody().name());
        assertEquals(desc, response.getBody().description());
        assertEquals(date, response.getBody().dueDate());

    }

    @Description("given that the name is too long When a creation request is sent Then server should return a unsuccessful response")
    @Test
    void NameReturn400() {
        String url = "http://localhost:" + port + "/";
        String name = "task_0000000000000000000000000000000000000000000001";
        String desc = "desc1";
        String date = "2024-12-01";

        TaskEntityRequest request = new TaskEntityRequest(name, desc, date);
        ResponseEntity<TaskEntityResponse> response = testRestTemplate.postForEntity(url, request, TaskEntityResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Description("given that the description is too long When a creation request is sent Then server should return a unsuccessful response")
    @Test
    void DescrReturn400() {
        String url = "http://localhost:" + port + "/";
        String name = "description_00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001";
        String desc = "desc1";
        String date = "2024-12-01";

        TaskEntityRequest request = new TaskEntityRequest(name, desc, date);
        ResponseEntity<TaskEntityResponse> response = testRestTemplate.postForEntity(url, request, TaskEntityResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Description("given that the date has passed When a creation request is sent Then server should return an unsuccessful response")
    @Test
    void DateReturn400() {
        String url = "http://localhost:" + port + "/";
        String name = "task1";
        String desc = "desc1";
        String date = "1900-12-01";

        TaskEntityRequest request = new TaskEntityRequest(name, desc, date);
        ResponseEntity<TaskEntityResponse> response = testRestTemplate.postForEntity(url, request, TaskEntityResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    /*GET ALL*/
    @Description("given a task is created WHEN the task list is executed THEN the server should return all the task that are in database")
    @Test
    void listTask200() {

        //Task Creation
        String urlPost = "http://localhost:" + port + "/";
        String name = "task1";
        String desc = "desc1";
        String date = "2024-12-01";

        TaskEntityRequest request = new TaskEntityRequest(name, desc, date);
        ResponseEntity<TaskEntityResponse> responsePost = testRestTemplate.postForEntity(urlPost, request, TaskEntityResponse.class);

        //Get Task list

        String urlGet = "http://localhost:" + port + "/tasks";
        ResponseEntity<TaskEntityResponse[]> responseGet = testRestTemplate.getForEntity(urlGet, TaskEntityResponse[].class);

        List<TaskEntityResponse> responseList = Arrays.stream(Objects.requireNonNull(responseGet.getBody())).filter(t -> t.idTask().equals(Objects.requireNonNull(responsePost.getBody()).idTask())).toList();

        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        assertEquals(1, responseList.size());
    }

    @Description("given a task is created WHEN the task requested by id THEN the server should return an specific task")
    @Test
    void rTask200() {
        //Task Creation
        String urlPost = "http://localhost:" + port + "/";
        String name = "task2";
        String desc = "desc2";
        String date = "2024-12-01";

        TaskEntityRequest request = new TaskEntityRequest(name, desc, date);
        ResponseEntity<TaskEntityResponse> responsePost = testRestTemplate.postForEntity(urlPost, request, TaskEntityResponse.class);

        //Get Task by Id
        String urlGet = "http://localhost:" + port + "/task/" + Objects.requireNonNull(responsePost.getBody()).idTask();
        ResponseEntity<TaskEntityResponse> responseGet = testRestTemplate.getForEntity(urlGet, TaskEntityResponse.class);

        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        assertEquals(Objects.requireNonNull(responsePost.getBody()).idTask(), responseGet.getBody().idTask());

    }

    @Description("WHEN a task is requested by id AND it does not exist THEN the server should return an unsuccessful response ")
    @Test
    void rTask404() {
        //Get Task by Id
        String urlGet = "http://localhost:" + port + "/task/" + UUID.randomUUID();
        ResponseEntity<TaskEntityResponse> responseGet = testRestTemplate.getForEntity(urlGet, TaskEntityResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

}