package com.grandstrand.api;

import com.grandstrand.domain.Task;
import com.grandstrand.dto.TaskDto;
import com.grandstrand.service.TaskService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API controller for Task operations.
 */
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        if (taskService == null) {
            throw new IllegalArgumentException("TaskService cannot be null");
        }
        this.taskService = taskService;
    }

    public TaskDto createTask(TaskDto dto) {
        Task task = taskService.addTask(dto.getTaskId(), dto.getName(), dto.getDescription());
        return toDto(task);
    }

    public TaskDto getTask(String taskId) {
        Task task = taskService.getTask(taskId);
        return toDto(task);
    }

    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public TaskDto updateTask(String taskId, TaskDto dto) {
        if (dto.getName() != null && dto.getDescription() != null) {
            taskService.updateTask(taskId, dto.getName(), dto.getDescription());
        } else if (dto.getName() != null) {
            taskService.updateTaskName(taskId, dto.getName());
        } else if (dto.getDescription() != null) {
            taskService.updateTaskDescription(taskId, dto.getDescription());
        }
        return toDto(taskService.getTask(taskId));
    }

    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
    }

    private TaskDto toDto(Task task) {
        return new TaskDto(task.getTaskId(), task.getName(), task.getDescription());
    }
}
