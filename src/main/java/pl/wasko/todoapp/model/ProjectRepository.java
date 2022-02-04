package pl.wasko.todoapp.model;

import java.util.List;

public interface ProjectRepository {
    List<Project> findAll();
}
