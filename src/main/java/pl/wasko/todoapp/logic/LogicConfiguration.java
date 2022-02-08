package pl.wasko.todoapp.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.ProjectRepository;
import pl.wasko.todoapp.model.TaskGroupRepository;

@Configuration

public class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config) {
        return new ProjectService(repository, taskGroupRepository, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository){
        return new TaskGroupService(taskGroupRepository);
    }

}

