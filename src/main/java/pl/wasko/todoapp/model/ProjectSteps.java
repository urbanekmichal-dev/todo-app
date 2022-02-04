package pl.wasko.todoapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "project_steps")
public class ProjectSteps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime daysToDeadline;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;
}
