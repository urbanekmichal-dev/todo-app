package pl.wasko.todoapp.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.wasko.todoapp.model.Task;

@Getter
@Setter
public class GroupTaskReadModel {
    private String description;
    private boolean done;

    GroupTaskReadModel(Task source){
        description = source.getDescription();
        done =source.isDone();
    }
}
