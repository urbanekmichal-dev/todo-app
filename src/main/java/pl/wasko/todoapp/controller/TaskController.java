package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskRepository;

import java.util.List;


@AllArgsConstructor
@RepositoryRestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    @GetMapping(value = "/tasks",params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping(value = "/tasks")
    ResponseEntity<?> readAllTasks(Pageable page){
        logger.warn("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(page).getContent());
    }
}
