package pl.wasko.todoapp.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wasko.todoapp.model.Project;
import pl.wasko.todoapp.model.ProjectRepository;
import pl.wasko.todoapp.model.TaskGroup;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository,JpaRepository<Project,Integer> {
    @Override
    @Query("from Project p join fetch p.projectSteps")
    List<Project> findAll();
}
