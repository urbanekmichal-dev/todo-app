package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wasko.todoapp.TaskConfigurationProperties;
@AllArgsConstructor
@RestController
@RequestMapping("/info")
public class InfoController {

    private final DataSourceProperties dataSourceProperties;
    private final TaskConfigurationProperties allowMultipleTasksFromTemplate;

    @GetMapping("/url")
    String url() {
        return dataSourceProperties.getUrl();
    }

    @GetMapping("/prop")
    boolean myProp() {
        return allowMultipleTasksFromTemplate.getTemplate().isAllowMultipleTasks();
    }
}

