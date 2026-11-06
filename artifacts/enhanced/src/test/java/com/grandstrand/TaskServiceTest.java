package com.grandstrand;

import com.grandstrand.domain.Task;
import com.grandstrand.repository.TaskRepository;
import com.grandstrand.repository.InMemoryTaskRepository;
import com.grandstrand.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TaskService with dependency injection.
 */
class TaskServiceTest {
    private TaskService taskService;
    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
        taskService = new TaskService(repository);
    }

    @Test
    void testAddTask() {
        Task task = taskService.addTask("T001", "Test Task", "Test Description");
        assertNotNull(task);
        assertEquals("T001", task.getTaskId());
        assertEquals(1, taskService.getTaskCount());
    }

    @Test
    void testAddDuplicateTask() {
        taskService.addTask("T001", "Test Task", "Test Description");
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask("T001", "Another Task", "Another Description");
        });
    }

    @Test
    void testGetTask() {
        taskService.addTask("T001", "Test Task", "Test Description");
        Task task = taskService.getTask("T001");
        assertEquals("Test Task", task.getName());
    }

    @Test
    void testUpdateTaskName() {
        taskService.addTask("T001", "Test Task", "Test Description");
        taskService.updateTaskName("T001", "Updated Task");
        assertEquals("Updated Task", taskService.getTask("T001").getName());
    }

    @Test
    void testDeleteTask() {
        taskService.addTask("T001", "Test Task", "Test Description");
        taskService.deleteTask("T001");
        assertEquals(0, taskService.getTaskCount());
    }

    @Test
    void testHasTask() {
        taskService.addTask("T001", "Test Task", "Test Description");
        assertTrue(taskService.hasTask("T001"));
        assertFalse(taskService.hasTask("NONEXISTENT"));
    }

    @Test
    void testIsEmpty() {
        assertTrue(taskService.isEmpty());
        taskService.addTask("T001", "Test Task", "Test Description");
        assertFalse(taskService.isEmpty());
    }
}
