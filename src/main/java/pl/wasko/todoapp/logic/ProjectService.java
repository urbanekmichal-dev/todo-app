package pl.wasko.todoapp.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.*;
import pl.wasko.todoapp.model.projection.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties taskConfigurationProperties;
    private final TaskGroupService taskGroupService;

    public List<ProjectReadModel> readAllProjects(){
        return projectRepository.findAll().stream().map(ProjectReadModel::new).collect(Collectors.toList());
    }

    public ProjectReadModel createProject(ProjectWriteModel projectWriteModel){
        Project project = projectRepository.save(projectWriteModel.toProject());
        return new ProjectReadModel(project);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) throws IllegalStateException {
        if (!taskConfigurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
       return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getProjectSteps().stream()
                                    .map(step -> {
                                        var task = new GroupTaskWriteModel();
                                        task.setDescription(step.getDescription());
                                        task.setDeadline(deadline.plusDays(step.getDaysToDeadline()));
                                        return task;
                                    }).collect(Collectors.toSet())
                    );
                    return taskGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }



}
