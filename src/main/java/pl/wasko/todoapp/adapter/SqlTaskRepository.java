package pl.wasko.todoapp.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository()
 interface SqlTaskRepository extends TaskRepository,JpaRepository<Task,Integer> {
 @Override
 @Query(nativeQuery = true,value = "select count(*)>0 from tasks where id=:id")
 boolean existsById(@Param("id") Integer id);

 @Override
 boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

 List<Task> findAllByDoneIsFalseAndDeadlineIsBeforeOrDeadlineIsNull(LocalDateTime today);
}
