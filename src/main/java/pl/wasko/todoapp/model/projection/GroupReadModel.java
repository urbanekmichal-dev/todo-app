package pl.wasko.todoapp.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskGroup;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupReadModel {
    private int id;
    private String description;
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;
    private boolean done;

    public GroupReadModel(TaskGroup source)
    {
        id= source.getId();
        description =source.getDescription();
        source.getTasks().stream().
                map(Task::getDeadline).
                max(LocalDateTime::compareTo).
                ifPresent(date -> deadline =date);
        tasks = source.getTasks().stream().
                map(GroupTaskReadModel::new).
                collect(Collectors.toSet());
        done =source.isDone();
    }
}
