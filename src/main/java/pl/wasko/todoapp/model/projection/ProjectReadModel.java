package pl.wasko.todoapp.model.projection;


import lombok.Getter;
import lombok.Setter;
import pl.wasko.todoapp.model.Project;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProjectReadModel {
    private String description;
    private Set<ProjectStepsReadModel> projectStepsReadModelSet;
    private Set<GroupReadModel> groupReadModelSet;

    public ProjectReadModel(Project project){
        this.description=project.getDescription();
        this.projectStepsReadModelSet = project.getProjectSteps().stream().map(ProjectStepsReadModel::new).collect(Collectors.toSet());
        this.groupReadModelSet = project.getTaskGroups().stream().map(GroupReadModel::new).collect(Collectors.toSet());
    }
}
