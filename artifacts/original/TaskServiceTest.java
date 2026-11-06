import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Assertions;
import java.util.Collection;
import java.util.List;

/**
 * Comprehensive test suite for the TaskService class.
 * Tests all requirements including CRUD operations, validation, and edge cases.
 * 
 * @author CS-305 Student
 * @version 1.0
 */
public class TaskServiceTest {
    
    private TaskService taskService;
    
    // Test data constants
    private static final String VALID_TASK_ID = "12345";
    private static final String VALID_NAME = "Test Task";
    private static final String VALID_DESCRIPTION = "Test description";
    
    private static final String SECOND_TASK_ID = "67890";
    private static final String SECOND_NAME = "Second Task";
    private static final String SECOND_DESCRIPTION = "Second description";
    
    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
    }
    
    @Nested
    @DisplayName("Add Task Tests")
    class AddTaskTests {
        
        @Test
        @DisplayName("Should add task with valid parameters")
        public void testAddValidTask() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Task task = taskService.getTask(VALID_TASK_ID);
            
            Assertions.assertNotNull(task);
            Assertions.assertEquals(VALID_TASK_ID, task.getTaskId());
            Assertions.assertEquals(VALID_NAME, task.getName());
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should add Task object directly")
        public void testAddTaskObject() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            taskService.addTask(task);
            
            Task retrievedTask = taskService.getTask(VALID_TASK_ID);
            Assertions.assertNotNull(retrievedTask);
            Assertions.assertEquals(task, retrievedTask);
        }
        
        @Test
        @DisplayName("Should reject duplicate task ID")
        public void testAddTaskWithDuplicateId() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.addTask(VALID_TASK_ID, "Different Name", "Different Description")
            );
            Assertions.assertTrue(exception.getMessage().contains("already exists"));
        }
        
        @Test
        @DisplayName("Should reject null task ID")
        public void testAddTaskWithNullId() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.addTask(null, VALID_NAME, VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Task ID cannot be null", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject null Task object")
        public void testAddNullTaskObject() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.addTask((Task) null)
            );
            Assertions.assertEquals("Task cannot be null", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject invalid task parameters through Task constructor")
        public void testAddTaskWithInvalidParameters() {
            // Test invalid name
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.addTask(VALID_TASK_ID, null, VALID_DESCRIPTION));
            
            // Test invalid description
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.addTask(VALID_TASK_ID, VALID_NAME, null));
            
            // Test too long task ID
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.addTask("12345678901", VALID_NAME, VALID_DESCRIPTION));
        }
        
        @Test
        @DisplayName("Should add multiple tasks with different IDs")
        public void testAddMultipleTasks() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
            
            Assertions.assertNotNull(taskService.getTask(VALID_TASK_ID));
            Assertions.assertNotNull(taskService.getTask(SECOND_TASK_ID));
            Assertions.assertEquals(2, taskService.getTaskCount());
        }
    }
    
    @Nested
    @DisplayName("Delete Task Tests")
    class DeleteTaskTests {
        
        @BeforeEach
        public void setUpTasks() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
        }
        
        @Test
        @DisplayName("Should delete existing task")
        public void testDeleteExistingTask() {
            Assertions.assertTrue(taskService.hasTask(VALID_TASK_ID));
            
            taskService.deleteTask(VALID_TASK_ID);
            
            Assertions.assertFalse(taskService.hasTask(VALID_TASK_ID));
            Assertions.assertNull(taskService.getTask(VALID_TASK_ID));
        }
        
        @Test
        @DisplayName("Should reject deleting non-existent task")
        public void testDeleteNonExistentTask() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.deleteTask("99999")
            );
            Assertions.assertTrue(exception.getMessage().contains("not found"));
        }
        
        @Test
        @DisplayName("Should reject null task ID for deletion")
        public void testDeleteWithNullId() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.deleteTask(null)
            );
            Assertions.assertEquals("Task ID cannot be null", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should update task count after deletion")
        public void testTaskCountAfterDeletion() {
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
            Assertions.assertEquals(2, taskService.getTaskCount());
            
            taskService.deleteTask(VALID_TASK_ID);
            Assertions.assertEquals(1, taskService.getTaskCount());
            
            taskService.deleteTask(SECOND_TASK_ID);
            Assertions.assertEquals(0, taskService.getTaskCount());
            Assertions.assertTrue(taskService.isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Update Task Tests")
    class UpdateTaskTests {
        
        @BeforeEach
        public void setUpTasks() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
        }
        
        @Test
        @DisplayName("Should update task name")
        public void testUpdateTaskName() {
            String newName = "Updated Name";
            taskService.updateTaskName(VALID_TASK_ID, newName);
            
            Task task = taskService.getTask(VALID_TASK_ID);
            Assertions.assertEquals(newName, task.getName());
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription()); // Should remain unchanged
        }
        
        @Test
        @DisplayName("Should update task description")
        public void testUpdateTaskDescription() {
            String newDescription = "Updated description";
            taskService.updateTaskDescription(VALID_TASK_ID, newDescription);
            
            Task task = taskService.getTask(VALID_TASK_ID);
            Assertions.assertEquals(VALID_NAME, task.getName()); // Should remain unchanged
            Assertions.assertEquals(newDescription, task.getDescription());
        }
        
        @Test
        @DisplayName("Should update both name and description")
        public void testUpdateTask() {
            String newName = "Updated Name";
            String newDescription = "Updated description";
            taskService.updateTask(VALID_TASK_ID, newName, newDescription);
            
            Task task = taskService.getTask(VALID_TASK_ID);
            Assertions.assertEquals(newName, task.getName());
            Assertions.assertEquals(newDescription, task.getDescription());
        }
        
        @Test
        @DisplayName("Should reject updating non-existent task name")
        public void testUpdateNonExistentTaskName() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.updateTaskName("99999", "New Name")
            );
            Assertions.assertTrue(exception.getMessage().contains("not found"));
        }
        
        @Test
        @DisplayName("Should reject updating non-existent task description")
        public void testUpdateNonExistentTaskDescription() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> taskService.updateTaskDescription("99999", "New Description")
            );
            Assertions.assertTrue(exception.getMessage().contains("not found"));
        }
        
        @Test
        @DisplayName("Should reject null task ID for updates")
        public void testUpdateWithNullId() {
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.updateTaskName(null, "New Name"));
            
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.updateTaskDescription(null, "New Description"));
            
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.updateTask(null, "New Name", "New Description"));
        }
        
        @Test
        @DisplayName("Should reject invalid update values")
        public void testUpdateWithInvalidValues() {
            // Test invalid name update
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.updateTaskName(VALID_TASK_ID, null));
            
            // Test invalid description update
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.updateTaskDescription(VALID_TASK_ID, null));
            
            // Test too long name
            Assertions.assertThrows(IllegalArgumentException.class, 
                () -> taskService.updateTaskName(VALID_TASK_ID, "This name is way too long for the requirements"));
        }
    }
    
    @Nested
    @DisplayName("Retrieve Task Tests")
    class RetrieveTaskTests {
        
        @BeforeEach
        public void setUpTasks() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
        }
        
        @Test
        @DisplayName("Should retrieve existing task")
        public void testGetExistingTask() {
            Task task = taskService.getTask(VALID_TASK_ID);
            
            Assertions.assertNotNull(task);
            Assertions.assertEquals(VALID_TASK_ID, task.getTaskId());
            Assertions.assertEquals(VALID_NAME, task.getName());
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should return null for non-existent task")
        public void testGetNonExistentTask() {
            Task task = taskService.getTask("99999");
            Assertions.assertNull(task);
        }
        
        @Test
        @DisplayName("Should return null for null task ID")
        public void testGetTaskWithNullId() {
            Task task = taskService.getTask(null);
            Assertions.assertNull(task);
        }
        
        @Test
        @DisplayName("Should check task existence")
        public void testHasTask() {
            Assertions.assertTrue(taskService.hasTask(VALID_TASK_ID));
            Assertions.assertTrue(taskService.hasTask(SECOND_TASK_ID));
            Assertions.assertFalse(taskService.hasTask("99999"));
            Assertions.assertFalse(taskService.hasTask(null));
        }
    }
    
    @Nested
    @DisplayName("Service State Tests")
    class ServiceStateTests {
        
        @Test
        @DisplayName("Should start empty")
        public void testInitialState() {
            Assertions.assertEquals(0, taskService.getTaskCount());
            Assertions.assertTrue(taskService.isEmpty());
        }
        
        @Test
        @DisplayName("Should track task count correctly")
        public void testTaskCount() {
            Assertions.assertEquals(0, taskService.getTaskCount());
            
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Assertions.assertEquals(1, taskService.getTaskCount());
            Assertions.assertFalse(taskService.isEmpty());
            
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
            Assertions.assertEquals(2, taskService.getTaskCount());
            
            taskService.deleteTask(VALID_TASK_ID);
            Assertions.assertEquals(1, taskService.getTaskCount());
            
            taskService.deleteTask(SECOND_TASK_ID);
            Assertions.assertEquals(0, taskService.getTaskCount());
            Assertions.assertTrue(taskService.isEmpty());
        }
        
        @Test
        @DisplayName("Should get all tasks")
        public void testGetAllTasks() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
            
            Collection<Task> allTasks = taskService.getAllTasks();
            Assertions.assertEquals(2, allTasks.size());
            
            // Verify tasks are in the collection
            boolean foundFirst = false, foundSecond = false;
            for (Task task : allTasks) {
                if (task.getTaskId().equals(VALID_TASK_ID)) foundFirst = true;
                if (task.getTaskId().equals(SECOND_TASK_ID)) foundSecond = true;
            }
            Assertions.assertTrue(foundFirst && foundSecond);
        }
        
        @Test
        @DisplayName("Should get all task IDs")
        public void testGetAllTaskIds() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
            
            Collection<String> allIds = taskService.getAllTaskIds();
            Assertions.assertEquals(2, allIds.size());
            Assertions.assertTrue(allIds.contains(VALID_TASK_ID));
            Assertions.assertTrue(allIds.contains(SECOND_TASK_ID));
        }
        
        @Test
        @DisplayName("Should clear all tasks")
        public void testClearAllTasks() {
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            taskService.addTask(SECOND_TASK_ID, SECOND_NAME, SECOND_DESCRIPTION);
            Assertions.assertEquals(2, taskService.getTaskCount());
            
            taskService.clearAllTasks();
            
            Assertions.assertEquals(0, taskService.getTaskCount());
            Assertions.assertTrue(taskService.isEmpty());
            Assertions.assertNull(taskService.getTask(VALID_TASK_ID));
            Assertions.assertNull(taskService.getTask(SECOND_TASK_ID));
        }
    }
    
    @Nested
    @DisplayName("Search Functionality Tests")
    class SearchFunctionalityTests {
        
        @BeforeEach
        public void setUpSearchTasks() {
            taskService.addTask("1", "Buy groceries", "Purchase milk, bread, and eggs from store");
            taskService.addTask("2", "Write report", "Complete quarterly financial report");
            taskService.addTask("3", "Call client", "Follow up with client about project status");
            taskService.addTask("4", "Buy tickets", "Purchase concert tickets online");
        }
        
        @Test
        @DisplayName("Should search tasks by name")
        public void testSearchTasksByName() {
            List<Task> results = taskService.searchTasksByName("buy");
            Assertions.assertEquals(2, results.size());
            
            // Verify both "Buy groceries" and "Buy tickets" are found
            boolean foundGroceries = false, foundTickets = false;
            for (Task task : results) {
                if (task.getName().equals("Buy groceries")) foundGroceries = true;
                if (task.getName().equals("Buy tickets")) foundTickets = true;
            }
            Assertions.assertTrue(foundGroceries && foundTickets);
        }
        
        @Test
        @DisplayName("Should search tasks by description")
        public void testSearchTasksByDescription() {
            List<Task> results = taskService.searchTasksByDescription("report");
            Assertions.assertEquals(1, results.size());
            Assertions.assertEquals("Write report", results.get(0).getName());
        }
        
        @Test
        @DisplayName("Should return empty list for no matches")
        public void testSearchWithNoMatches() {
            List<Task> nameResults = taskService.searchTasksByName("nonexistent");
            List<Task> descResults = taskService.searchTasksByDescription("nonexistent");
            
            Assertions.assertTrue(nameResults.isEmpty());
            Assertions.assertTrue(descResults.isEmpty());
        }
        
        @Test
        @DisplayName("Should handle null and empty search queries")
        public void testSearchWithInvalidQueries() {
            Assertions.assertTrue(taskService.searchTasksByName(null).isEmpty());
            Assertions.assertTrue(taskService.searchTasksByName("").isEmpty());
            Assertions.assertTrue(taskService.searchTasksByName("   ").isEmpty());
            
            Assertions.assertTrue(taskService.searchTasksByDescription(null).isEmpty());
            Assertions.assertTrue(taskService.searchTasksByDescription("").isEmpty());
            Assertions.assertTrue(taskService.searchTasksByDescription("   ").isEmpty());
        }
        
        @Test
        @DisplayName("Should perform case-insensitive search")
        public void testCaseInsensitiveSearch() {
            List<Task> upperResults = taskService.searchTasksByName("BUY");
            List<Task> lowerResults = taskService.searchTasksByName("buy");
            List<Task> mixedResults = taskService.searchTasksByName("Buy");
            
            Assertions.assertEquals(2, upperResults.size());
            Assertions.assertEquals(2, lowerResults.size());
            Assertions.assertEquals(2, mixedResults.size());
        }
    }
    
    @Nested
    @DisplayName("Object Methods Tests")
    class ObjectMethodsTests {
        
        @Test
        @DisplayName("toString should show task count")
        public void testToString() {
            String emptyString = taskService.toString();
            Assertions.assertTrue(emptyString.contains("taskCount=0"));
            
            taskService.addTask(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            String oneTaskString = taskService.toString();
            Assertions.assertTrue(oneTaskString.contains("taskCount=1"));
        }
    }
    
    @Nested
    @DisplayName("Edge Cases and Stress Tests")
    class EdgeCasesTests {
        
        @Test
        @DisplayName("Should handle maximum length parameters")
        public void testMaxLengthParameters() {
            String maxId = "1234567890"; // 10 characters
            String maxName = "12345678901234567890"; // 20 characters
            String maxDesc = "12345678901234567890123456789012345678901234567890"; // 50 characters
            
            Assertions.assertDoesNotThrow(() -> {
                taskService.addTask(maxId, maxName, maxDesc);
            });
            
            Task task = taskService.getTask(maxId);
            Assertions.assertNotNull(task);
            Assertions.assertEquals(maxId, task.getTaskId());
            Assertions.assertEquals(maxName, task.getName());
            Assertions.assertEquals(maxDesc, task.getDescription());
        }
        
        @Test
        @DisplayName("Should handle special characters in task data")
        public void testSpecialCharacters() {
            String specialId = "!@#$%^&*()"; // 10 characters with special chars
            String specialName = "Task with émojis 🚀";
            String specialDesc = "Description with newlines\nand tabs\t and quotes\"\'"; 
            
            Assertions.assertDoesNotThrow(() -> {
                taskService.addTask(specialId, specialName, specialDesc);
            });
            
            Task task = taskService.getTask(specialId);
            Assertions.assertNotNull(task);
            Assertions.assertEquals(specialId, task.getTaskId());
            Assertions.assertEquals(specialName, task.getName());
            Assertions.assertEquals(specialDesc, task.getDescription());
        }
        
        @Test
        @DisplayName("Should maintain data integrity during concurrent-like operations")
        public void testDataIntegrity() {
            // Add multiple tasks
            for (int i = 0; i < 10; i++) {
                taskService.addTask("task" + i, "Task " + i, "Description " + i);
            }
            
            // Verify all tasks exist
            Assertions.assertEquals(10, taskService.getTaskCount());
            
            // Update some tasks
            for (int i = 0; i < 5; i++) {
                taskService.updateTaskName("task" + i, "Updated Task " + i);
            }
            
            // Delete some tasks
            for (int i = 5; i < 10; i++) {
                taskService.deleteTask("task" + i);
            }
            
            // Verify final state
            Assertions.assertEquals(5, taskService.getTaskCount());
            
            for (int i = 0; i < 5; i++) {
                Task task = taskService.getTask("task" + i);
                Assertions.assertNotNull(task);
                Assertions.assertEquals("Updated Task " + i, task.getName());
            }
            
            for (int i = 5; i < 10; i++) {
                Assertions.assertNull(taskService.getTask("task" + i));
            }
        }
    }
}
