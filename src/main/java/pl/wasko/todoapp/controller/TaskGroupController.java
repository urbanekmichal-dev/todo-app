package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wasko.todoapp.logic.TaskGroupService;
import pl.wasko.todoapp.model.projection.GroupReadModel;
import pl.wasko.todoapp.model.projection.GroupTaskReadModel;
import pl.wasko.todoapp.model.projection.GroupTaskWriteModel;
import pl.wasko.todoapp.model.projection.GroupWriteModel;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/groups")
public class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService taskGroupService;


    @PostMapping("")
    ResponseEntity<GroupReadModel> addGroup(@RequestBody GroupWriteModel model){
        GroupReadModel groupReadModel= taskGroupService.createGroup(model);
        return ResponseEntity.created(URI.create("/"+groupReadModel.getId())).body(groupReadModel);
    }

    @GetMapping("")
    ResponseEntity<List<GroupReadModel>> getAllGroups(){
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable int id){
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    ResponseEntity<List<GroupTaskReadModel>> getTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(taskGroupService.readTasksFromGroup(id));
    }

}
