package pl.wasko.todoapp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task_groups")
public class TaskGroup extends TaskSuperClass{
    private int id;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;


}
