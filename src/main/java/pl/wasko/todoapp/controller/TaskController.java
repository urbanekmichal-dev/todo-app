package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@AllArgsConstructor
@Controller
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    @RequestMapping(method = RequestMethod.GET,value = "/tasks",params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET,value = "/tasks")
    ResponseEntity<?> readAllTasks(Pageable page){
        logger.warn("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(page).getContent());
    }
    @RequestMapping(method = RequestMethod.PUT,value = "/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate){
        if(!taskRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        taskRepository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET,value = "/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable int id){
        return taskRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/tasks")
    ResponseEntity<Task> addTask(@RequestBody @Valid Task task){
        Task result = taskRepository.save(task);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);

    }

}
