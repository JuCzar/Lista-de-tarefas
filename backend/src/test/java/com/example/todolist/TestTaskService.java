package com.example.todolist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api.Priority;
import com.example.api.Status;
import com.example.api.TaskDto;
import com.example.convert.TaskConvert;
import com.example.db.entity.TaskEntity;
import com.example.db.repository.TaskRepository;
import com.example.service.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestTaskService {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskConvert taskConvert;
    @InjectMocks
    private TaskService taskService;

    @Test
    public void testSaveTask() {
        final TaskDto taskDto = new TaskDto();
        final TaskEntity taskEntity = new TaskEntity();

        when(taskConvert.convertTaskDtoToTaskEntity(taskDto)).thenReturn(taskEntity);
        taskService.saveTask(taskDto);

        verify(taskConvert, times(1)).convertTaskDtoToTaskEntity(taskDto);
        verify(taskRepository, times(1)).save(taskEntity);
    }

    @Test
    public void testGetTaskById() {
        UUID id = UUID.randomUUID();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));
        TaskDto taskDto = new TaskDto();
        when(taskConvert.convertTaskEntityToTaskDto(taskEntity)).thenReturn(taskDto);

        TaskDto taskDtoResult = taskService.getTaskById(id);

        assertNotNull(taskDtoResult);
        assertEquals(taskDto, taskDtoResult);

        verify(taskRepository, times(1)).findById(id);
        verify(taskConvert, times(1)).convertTaskEntityToTaskDto(taskEntity);
    }

    @Test
    public void testDeleteTask() {
        UUID id = UUID.randomUUID();
        taskService.deleteTask(id);
        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(UUID.randomUUID());
        taskDto.setTitle("Título Atualizado");
        taskDto.setStatus(Status.CONCLUIDO);
        taskDto.setDescription("Descrição Atualizada");
        taskDto.setPriority(Priority.ALTA);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskDto.getId());
        when(taskRepository.findById(taskDto.getId())).thenReturn(Optional.of(taskEntity));

        taskService.updateTask(taskDto);

        verify(taskRepository, times(1)).findById(taskDto.getId());
        verify(taskRepository, times(1)).save(taskEntity);

        assertEquals("Título Atualizado", taskEntity.getTitle());
        assertEquals("Descrição Atualizada", taskEntity.getDescription());
        assertEquals(Status.CONCLUIDO, taskEntity.getStatus());
        assertEquals(Priority.ALTA, taskEntity.getPriority());
    }

    @Test
    public void testGetTaskList() {
        List<TaskEntity> taskEntityList = Arrays.asList(new TaskEntity(), new TaskEntity(), new TaskEntity());
        when(taskRepository.findAllByOrderByCreatedOnDesc()).thenReturn(taskEntityList);

        TaskDto taskDto = new TaskDto();
        TaskEntity taskEntity = new TaskEntity();
        // Aqui usamos Mockito.any() para aceitar qualquer TaskEntity
        when(taskConvert.convertTaskEntityToTaskDto(any(TaskEntity.class))).thenReturn(taskDto);

        List<TaskDto> taskDtoList = taskService.getTaskList();

        assertNotNull(taskDtoList);
        assertEquals(3, taskDtoList.size());
        verify(taskRepository, times(1)).findAllByOrderByCreatedOnDesc();
        // Verifica se o método foi chamado 3 vezes, uma para cada TaskEntity
        verify(taskConvert, times(3)).convertTaskEntityToTaskDto(any(TaskEntity.class));
    }
}
