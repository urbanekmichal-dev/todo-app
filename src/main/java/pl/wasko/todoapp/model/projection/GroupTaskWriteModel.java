package pl.wasko.todoapp.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskGroup;

import java.time.LocalDateTime;
@Getter
@Setter

public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public Task toTask(TaskGroup taskGroup){
        return new Task(description,deadline,taskGroup );
    }

}
