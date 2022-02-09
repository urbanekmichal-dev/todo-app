package pl.wasko.todoapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.wasko.todoapp.model.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class ProjectControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository repo;

    @Test
    void httpGet_returnsGivenProjects() throws Exception {
        //given
        Project project = new Project("project1",
                Set.of(new ProjectSteps("aa",2)),
                new HashSet<>());

        int id = repo.save(project).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/project/"+id)).andExpect(
                status().is2xxSuccessful()
        );

    }
}
