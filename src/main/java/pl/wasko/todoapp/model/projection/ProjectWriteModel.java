package pl.wasko.todoapp.model.projection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.wasko.todoapp.model.Project;
import pl.wasko.todoapp.model.ProjectSteps;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
public class ProjectWriteModel {
    private String description;
    private List<ProjectSteps> projectStepsList;


//    private Set<GroupWriteModel> groupWriteModelSet;
//    private Set<ProjectStepsWriteModel> projectStepsWriteModelSet;
    public ProjectWriteModel(){
        projectStepsList.add(new ProjectSteps());
    }

    public Project toProject(){
       var result = new Project();
       result.setDescription(description);
        projectStepsList.forEach(step-> step.setProject(result));
        result.setProjectSteps(new HashSet<>(projectStepsList));
//        result.setDescription(description);
//        result.setProjectSteps(projectStepsWriteModelSet.stream().map(ProjectStepsWriteModel::toProjectSteps).collect(Collectors.toSet()));
//        result.setTaskGroups(groupWriteModelSet.stream().map(GroupWriteModel::toGroup).collect(Collectors.toSet()));
        return result;
   }

}
