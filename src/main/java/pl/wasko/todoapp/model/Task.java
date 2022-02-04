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
public class Task extends TaskSuperClass{

    @Column()
    private LocalDateTime deadline;

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
