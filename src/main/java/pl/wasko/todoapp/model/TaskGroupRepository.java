package pl.wasko.todoapp.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup> findAll();
    Optional<TaskGroup> findById(Integer id);
    TaskGroup save(TaskGroup entity);
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
    boolean existsByDoneIsFalseAndGroup_Id(Integer id);
    List<Task> findAllById(Integer groupId);
}
