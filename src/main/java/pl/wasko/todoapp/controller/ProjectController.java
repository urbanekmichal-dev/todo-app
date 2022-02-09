package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.wasko.todoapp.model.Project;
import pl.wasko.todoapp.model.ProjectRepository;
import pl.wasko.todoapp.model.ProjectSteps;
import pl.wasko.todoapp.model.projection.ProjectReadModel;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
public class ProjectController {
    private final ProjectRepository projectRepository;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }


    @GetMapping("project/{id}")
    public ResponseEntity<Project> getProject(@PathVariable int id){
        Project projectFound=projectRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Project with id " + id+ " was not found")
        );

        return ResponseEntity.ok(projectFound);
    }
}
