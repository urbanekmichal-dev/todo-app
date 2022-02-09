package pl.wasko.todoapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "project")
    private Set<ProjectSteps> projectSteps;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<TaskGroup> taskGroups;

    public Project(String description, Set<ProjectSteps> projectSteps, Set<TaskGroup> taskGroups) {
        this.description = description;
        this.projectSteps = projectSteps;
        this.taskGroups = taskGroups;
    }
}
