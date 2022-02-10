package pl.wasko.todoapp.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.wasko.todoapp.model.Task;
import pl.wasko.todoapp.model.TaskGroup;
import pl.wasko.todoapp.model.TaskGroupRepository;
import pl.wasko.todoapp.model.TaskRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException() {
        //given
        TaskGroupRepository mockTaskGroupRepository = getTaskGroupRepository(true);
        var toTest = new TaskGroupService(mockTaskGroupRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Group has undone tasks");
    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        TaskGroupRepository mockTaskGroupRepository = getTaskGroupRepository(false);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        var toTest = new TaskGroupService(mockTaskGroupRepository);

        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("TaskGroup with given id not found");
    }

    @Test
    @DisplayName("should toggle a group")
    void toggleGroup_resultOK() {
        //given

        //TaskGroupRepository mockTaskGroupRepository = getTaskGroupRepository(false);
        InMemoryTaskGroupRepository memoryTaskGroupRepository = inMemoryTaskGroupRepository();

        TaskGroup taskGroup = new TaskGroup();
        //taskGroup.setId(0);
        taskGroup.setDone(true);

        memoryTaskGroupRepository.save(taskGroup);

        var toTest = new TaskGroupService(memoryTaskGroupRepository);

        //when
       toTest.toggleGroup(1);
        //then
        memoryTaskGroupRepository.findById(1);


        assertThat(memoryTaskGroupRepository.findById(1).get().isDone()).isEqualTo(false);
    }
    private InMemoryTaskGroupRepository inMemoryTaskGroupRepository(){
        return new InMemoryTaskGroupRepository();
    }

    private static class InMemoryTaskGroupRepository implements TaskGroupRepository{

        private Map<Integer, TaskGroup> map = new HashMap<>();
        private int index = 0;
        @Override
        public List<TaskGroup> findAll() {
            return null;
        }

        @Override
        public Optional<TaskGroup> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public TaskGroup save(TaskGroup entity) {
            if (entity.getId() == 0) {
                try {
                    var field = TaskGroup.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity,++index);

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
            return false;
        }

        @Override
        public boolean existsByDoneIsFalseAndGroup_Id(Integer id) {
            return false;
        }

        @Override
        public List<Task> findAllById(Integer id) {
            return null;
        }
    }

    private TaskGroupRepository getTaskGroupRepository(final boolean result) {
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockTaskGroupRepository;
    }
}