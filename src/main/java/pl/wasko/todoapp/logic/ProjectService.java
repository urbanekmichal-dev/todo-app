package pl.wasko.todoapp.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.*;
import pl.wasko.todoapp.model.projection.GroupReadModel;
import pl.wasko.todoapp.model.projection.ProjectReadModel;
import pl.wasko.todoapp.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties taskConfigurationProperties;

    public List<ProjectReadModel> readAllProjects(){
        return projectRepository.findAll().stream().map(ProjectReadModel::new).collect(Collectors.toList());
    }

    public ProjectReadModel createProject(ProjectWriteModel projectWriteModel){
        Project project = projectRepository.save(projectWriteModel.toProject());
        return new ProjectReadModel(project);
    }

    public GroupReadModel createGroup (int projectId, LocalDateTime deadline) throws IllegalStateException  {
    if(!taskConfigurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
        throw new IllegalStateException("Only one undone group from project is allowed");
    }
       TaskGroup taskGroup= projectRepository.findById(projectId).map(project -> {
            var result = new TaskGroup();
            result.setDescription(project.getDescription());
            result.setTasks(project.getProjectSteps().stream()
                    .map(step ->
                    new Task(step.getDescription(), deadline.plusDays(step.getDaysToDeadline())))
                    .collect(Collectors.toSet()));
            return result;

        }).orElseThrow(()->new IllegalArgumentException("Project with given id not found"));
    return new GroupReadModel(taskGroup);
    }
}
