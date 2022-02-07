package pl.wasko.todoapp.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.Project;
import pl.wasko.todoapp.model.ProjectRepository;
import pl.wasko.todoapp.model.TaskGroup;
import pl.wasko.todoapp.model.TaskGroupRepository;
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

    public GroupReadModel createGroup (int projectId, LocalDateTime deadline) throws Exception {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project with id "+projectId+" was not found"));
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setDescription(project.getDescription());
        taskGroup.setDone(false);
        taskGroup.setProject(project);


    }
}
