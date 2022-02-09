package pl.wasko.todoapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import pl.wasko.todoapp.model.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ProjectRepository repo;

    @Test
    void httpGet_returnsAllProjects(){
        //given
        int initial = repo.findAll().size();
        Project project = new Project("project1",
                Set.of(new ProjectSteps("aa",2)),
                new HashSet<>());
        repo.save(project);
        //when
        Project[] result = restTemplate.getForObject("http://localhost:"+port+"/projects",Project[].class);
        //then
        assertThat(result).hasSize(initial+1);

    }


}
