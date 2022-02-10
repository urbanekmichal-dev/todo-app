package pl.wasko.todoapp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task_groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @NotBlank(message = "Task's description must not be empty")
    protected String description;
    protected boolean done;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group")
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    public TaskGroup(Set<Task> tasks, Project project) {
        this.tasks = tasks;
        this.project = project;
    }
}
