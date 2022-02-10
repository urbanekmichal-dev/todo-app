package pl.wasko.todoapp.logic;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.TaskGroup;
import pl.wasko.todoapp.model.TaskGroupRepository;
import pl.wasko.todoapp.model.TaskRepository;
import pl.wasko.todoapp.model.projection.GroupReadModel;
import pl.wasko.todoapp.model.projection.GroupTaskReadModel;
import pl.wasko.todoapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestScope
public class TaskGroupService {
        private final TaskGroupRepository repository;
        private final TaskRepository taskRepository;

        public GroupReadModel createGroup(GroupWriteModel source){
             TaskGroup result =  repository.save(source.toGroup());
             return new GroupReadModel(result);
        }

        public List<GroupReadModel> readAll(){
                return repository.findAll().stream().map(GroupReadModel::new).collect(Collectors.toList());
        }

        public void toggleGroup(int groupId){
                if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){//TODO: Something doesn't work
                        throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
                }
                TaskGroup result =  repository.findById(groupId).orElseThrow(()-> new IllegalArgumentException("TaskGroup with given id not found"));
                result.setDone(!result.isDone());
                repository.save(result);
        }

        public List<GroupTaskReadModel> readTasksFromGroup(int id){
                return repository.findAllById(id).stream().map(GroupTaskReadModel::new).collect(Collectors.toList());
        }
}
