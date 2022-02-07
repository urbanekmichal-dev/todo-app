package pl.wasko.todoapp.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.wasko.todoapp.TaskConfigurationProperties;
import pl.wasko.todoapp.model.ProjectRepository;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskGroup;
import pl.wasko.todoapp.model.TaskGroupRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_openGroups_throwsIllegalStateExceptions() {
        //given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        //and
        TaskConfigurationProperties mocProperties = mockConfiguration(false);
        //system under test
        var toTest = new ProjectService(null,mockGroupRepository,mocProperties);
        //when
        var exception = catchThrowable(()->toTest.createGroup(0,LocalDateTime.now()));
        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("one undone group");

        //when+then
//        assertThatThrownBy(()->{
//            toTest.createGroup(0, LocalDateTime.now());
//        }).isInstanceOf(IllegalStateException.class);

        //assertThatIllegalStateException().isThrownBy(()-> toTest.createGroup(0,LocalDateTime.now()));

    }

    @Test
    @DisplayName("should throw IllegalStateException when configuration ok and no projects for a given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentsExceptions() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        //and
        TaskConfigurationProperties mocProperties = mockConfiguration(true);
        //system under test
        var toTest = new ProjectService(mockRepository, null, mocProperties);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));
        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and no groups and no projects projects fo a given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExists_noProjects_throwsIllegalArgumentsExceptions() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskGroupRepository taskGroupRepository = groupRepositoryReturning(false);

        //and
        TaskConfigurationProperties mocProperties = mockConfiguration(true);
        //system under test
        var toTest = new ProjectService(mockRepository, taskGroupRepository, mocProperties);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));
        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup(){
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskConfigurationProperties mocProperties = mockConfiguration(true);

        //when

        //then
    }

    private TaskGroupRepository inMemoryGroupRepository(){
    new TaskGroupRepository(){
        private Map<Integer, TaskGroup> map = new HashMap<>();
        private int index = 0;

        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<TaskGroup> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public TaskGroup save(TaskGroup entity) {
            if(entity.getId()!=0){
                map.put(entity.getId(),entity);
            } else {
                map.put(++index,entity);
            }
            return null;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
            return false;
        }
    }
    }


    private TaskGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private TaskConfigurationProperties mockConfiguration(final boolean result) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        //and
        var mocProperties = mock(TaskConfigurationProperties.class);
        when(mocProperties.getTemplate()).thenReturn(mockTemplate);
        return mocProperties;
    }


}