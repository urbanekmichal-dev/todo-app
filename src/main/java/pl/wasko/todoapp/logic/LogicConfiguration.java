package pl.wasko.todoapp.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.ProjectRepository;
import pl.wasko.todoapp.model.TaskGroupRepository;
import pl.wasko.todoapp.model.TaskRepository;

@Configuration

public class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config,
            final TaskGroupService taskGroupService) {
        return new ProjectService(repository, taskGroupRepository, config,taskGroupService);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository){
        return new TaskGroupService(taskGroupRepository,taskRepository);
    }

}

