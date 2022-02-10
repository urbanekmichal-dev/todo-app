package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wasko.todoapp.logic.TaskService;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@AllArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private final TaskService service;

    @RequestMapping(method = RequestMethod.GET, value = "", params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return service.findAllAsync().thenApply(ResponseEntity::ok);

    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    ResponseEntity<?> readAllTasks(Pageable page) {
        logger.warn("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(page).getContent());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.findById(id).ifPresent(task->{task.updateFrom(toUpdate);
            taskRepository.save(task);
        });
        return ResponseEntity.noContent().build();
    }
    @Transactional
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
      taskRepository.findById(id).ifPresent(task->task.setDone(!task.isDone()));

        //throw new RuntimeException();
       return ResponseEntity.noContent().build();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<Task> getTask(@PathVariable int id) {
        return taskRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    ResponseEntity<Task> addTask(@RequestBody @Valid Task task) {
        Task result = taskRepository.save(task);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);

    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state){
        return ResponseEntity.ok(taskRepository.findByDone(state));
    }

    @GetMapping("/today")
    ResponseEntity<List<Task>> readTasksForToday(){
        return ResponseEntity.ok(taskRepository.findAllByDoneIsFalseAndDeadlineIsBeforeOrDeadlineIsNull(LocalDateTime.now()));
    }

}
