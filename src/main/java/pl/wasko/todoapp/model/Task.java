package pl.wasko.todoapp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    private boolean done;
    @Column()
    private LocalDateTime deadline;

//    @AttributeOverrides({
//            @AttributeOverride(column = @Column(name = "updateOn"), name = "updatedOn")
//    })

    @Embedded
    private BaseAuditableEntity audit = new BaseAuditableEntity();
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    @Getter(AccessLevel.PACKAGE)
    private TaskGroup group;

    public void updateFrom(final Task source){
        this.description=source.description;
        this.done=source.done;
        this.deadline=source.deadline;
        this.group=source.group;
    }

}
