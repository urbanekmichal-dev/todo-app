package pl.wasko.todoapp.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskGroup;
import pl.wasko.todoapp.model.TaskGroupRepository;

import java.util.List;

@Repository
public interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup,Integer> {
    @Override
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);

    @Override
   List<Task> findAllById(Integer groupId);

    @Override
    boolean existsByDoneIsFalseAndId(Integer projectId);


}
