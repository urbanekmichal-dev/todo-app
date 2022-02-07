package pl.wasko.todoapp.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.wasko.todoapp.model.ProjectSteps;

@Getter
@Setter
public class ProjectStepsWriteModel {
    private String description;
    private int daysToDeadLine;

    public ProjectSteps toProjectSteps(){
        return new ProjectSteps(description,daysToDeadLine);
    }
}
