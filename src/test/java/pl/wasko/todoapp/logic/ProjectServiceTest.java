package pl.wasko.todoapp.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.TaskGroupRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_openGroups_throwsIllegalStateExceptions() {
        //given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        //and
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);
        //and
        var mocProperties = mock(TaskConfigurationProperties.class);
        when(mocProperties.getTemplate()).thenReturn(mockTemplate);
        //system under test
        var toTest = new ProjectService(null,mockGroupRepository,mocProperties);
        //when
        toTest.createGroup(0, LocalDateTime.now());
        //then
        assertTrue(mockGroupRepository.findById(2).isPresent());
    }
}