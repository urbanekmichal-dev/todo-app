package pl.wasko.todoapp.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.wasko.todoapp.model.Project;
import pl.wasko.todoapp.model.ProjectSteps;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProjectStepsReadModel {
    private String description;
    private int daysToDeadline;

    public ProjectStepsReadModel(ProjectSteps projectSteps){
        this.description = projectSteps.getDescription();
        this.daysToDeadline = projectSteps.getDaysToDeadline();
    }
}
