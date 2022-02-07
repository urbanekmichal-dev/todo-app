package pl.wasko.todoapp.model.projection;

import pl.wasko.todoapp.model.Project;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectWriteModel {
    private String description;
    private Set<GroupWriteModel> groupWriteModelSet;
    private Set<ProjectStepsWriteModel> projectStepsWriteModelSet;

    public Project toProject(){
        var result = new Project();
        result.setDescription(description);
        result.setProjectSteps(projectStepsWriteModelSet.stream().map(ProjectStepsWriteModel::toProjectSteps).collect(Collectors.toSet()));
        result.setTaskGroups(groupWriteModelSet.stream().map(GroupWriteModel::toGroup).collect(Collectors.toSet()));
        return result;
    }

}
