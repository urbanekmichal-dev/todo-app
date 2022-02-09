package pl.wasko.todoapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskRepository;

import java.time.LocalDateTime;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;


    @Test
    void httpGet_returnsAllTasks(){
        //given
        int initial = repo.findAll().size();
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar",LocalDateTime.now()));

        //when
        Task[] result = restTemplate.getForObject("http://localhost:"+port+"/tasks",Task[].class);
        //then
        assertThat(result).hasSize(initial+2);
    }

    @Test
    void httpPost_addNewTask(){
        //given
        int initial = repo.findAll().size();

        Task newTask = new Task("bar",LocalDateTime.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> httpEntity = new HttpEntity<>(newTask, headers);

        //when
        Task result=restTemplate.postForObject("http://localhost:"+port+"/tasks",httpEntity,Task.class);
        //then
        assertThat(result!=null).isTrue();
        assertThat(result.getDescription()).isEqualTo("bar");
    }

}