import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Assertions;

/**
 * Comprehensive test suite for the Task class.
 * Tests all requirements including validation, field constraints, and edge cases.
 * 
 * @author CS-305 Student
 * @version 1.0
 */
public class TaskTest {
    
    // Test data constants
    private static final String VALID_TASK_ID = "12345";
    private static final String VALID_NAME = "Test Task";
    private static final String VALID_DESCRIPTION = "This is a test task description";
    
    // Boundary test data
    private static final String MAX_LENGTH_TASK_ID = "1234567890"; // 10 characters
    private static final String MAX_LENGTH_NAME = "12345678901234567890"; // 20 characters
    private static final String MAX_LENGTH_DESCRIPTION = "12345678901234567890123456789012345678901234567890"; // 50 characters
    
    private static final String TOO_LONG_TASK_ID = "12345678901"; // 11 characters
    private static final String TOO_LONG_NAME = "123456789012345678901"; // 21 characters
    private static final String TOO_LONG_DESCRIPTION = "123456789012345678901234567890123456789012345678901"; // 51 characters
    
    @Nested
    @DisplayName("Task Creation Tests")
    class TaskCreationTests {
        
        @Test
        @DisplayName("Should create task with valid parameters")
        public void testValidTaskCreation() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            
            Assertions.assertEquals(VALID_TASK_ID, task.getTaskId());
            Assertions.assertEquals(VALID_NAME, task.getName());
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should create task with maximum length parameters")
        public void testTaskCreationWithMaxLengths() {
            Task task = new Task(MAX_LENGTH_TASK_ID, MAX_LENGTH_NAME, MAX_LENGTH_DESCRIPTION);
            
            Assertions.assertEquals(MAX_LENGTH_TASK_ID, task.getTaskId());
            Assertions.assertEquals(MAX_LENGTH_NAME, task.getName());
            Assertions.assertEquals(MAX_LENGTH_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should create task with minimum length parameters")
        public void testTaskCreationWithMinLengths() {
            Task task = new Task("1", "A", "B");
            
            Assertions.assertEquals("1", task.getTaskId());
            Assertions.assertEquals("A", task.getName());
            Assertions.assertEquals("B", task.getDescription());
        }
    }
    
    @Nested
    @DisplayName("Task ID Validation Tests")
    class TaskIdValidationTests {
        
        @Test
        @DisplayName("Should reject null task ID")
        public void testTaskIdNull() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(null, VALID_NAME, VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Task ID cannot be null", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject empty task ID")
        public void testTaskIdEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task("", VALID_NAME, VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Task ID cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject whitespace-only task ID")
        public void testTaskIdWhitespaceOnly() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task("   ", VALID_NAME, VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Task ID cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject task ID longer than 10 characters")
        public void testTaskIdTooLong() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(TOO_LONG_TASK_ID, VALID_NAME, VALID_DESCRIPTION)
            );
            Assertions.assertTrue(exception.getMessage().contains("cannot be longer than 10 characters"));
        }
        
        @Test
        @DisplayName("Should accept task ID with exactly 10 characters")
        public void testTaskIdExactly10Characters() {
            Assertions.assertDoesNotThrow(() -> {
                new Task(MAX_LENGTH_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            });
        }
    }
    
    @Nested
    @DisplayName("Name Validation Tests")
    class NameValidationTests {
        
        @Test
        @DisplayName("Should reject null name")
        public void testNameNull() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, null, VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Name cannot be null", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject empty name")
        public void testNameEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, "", VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Name cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject whitespace-only name")
        public void testNameWhitespaceOnly() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, "   ", VALID_DESCRIPTION)
            );
            Assertions.assertEquals("Name cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject name longer than 20 characters")
        public void testNameTooLong() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, TOO_LONG_NAME, VALID_DESCRIPTION)
            );
            Assertions.assertTrue(exception.getMessage().contains("cannot be longer than 20 characters"));
        }
        
        @Test
        @DisplayName("Should accept name with exactly 20 characters")
        public void testNameExactly20Characters() {
            Assertions.assertDoesNotThrow(() -> {
                new Task(VALID_TASK_ID, MAX_LENGTH_NAME, VALID_DESCRIPTION);
            });
        }
    }
    
    @Nested
    @DisplayName("Description Validation Tests")
    class DescriptionValidationTests {
        
        @Test
        @DisplayName("Should reject null description")
        public void testDescriptionNull() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, VALID_NAME, null)
            );
            Assertions.assertEquals("Description cannot be null", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject empty description")
        public void testDescriptionEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, VALID_NAME, "")
            );
            Assertions.assertEquals("Description cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject whitespace-only description")
        public void testDescriptionWhitespaceOnly() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, VALID_NAME, "   ")
            );
            Assertions.assertEquals("Description cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject description longer than 50 characters")
        public void testDescriptionTooLong() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> new Task(VALID_TASK_ID, VALID_NAME, TOO_LONG_DESCRIPTION)
            );
            Assertions.assertTrue(exception.getMessage().contains("cannot be longer than 50 characters"));
        }
        
        @Test
        @DisplayName("Should accept description with exactly 50 characters")
        public void testDescriptionExactly50Characters() {
            Assertions.assertDoesNotThrow(() -> {
                new Task(VALID_TASK_ID, VALID_NAME, MAX_LENGTH_DESCRIPTION);
            });
        }
    }
    
    @Nested
    @DisplayName("Field Update Tests")
    class FieldUpdateTests {
        
        private Task task;
        
        @BeforeEach
        public void setUp() {
            task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
        }
        
        @Test
        @DisplayName("Should update name successfully")
        public void testSetValidName() {
            String newName = "Updated Name";
            task.setName(newName);
            Assertions.assertEquals(newName, task.getName());
        }
        
        @Test
        @DisplayName("Should update description successfully")
        public void testSetValidDescription() {
            String newDescription = "Updated description";
            task.setDescription(newDescription);
            Assertions.assertEquals(newDescription, task.getDescription());
        }
        
        @Test
        @DisplayName("Should reject null name update")
        public void testSetNameNull() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> task.setName(null)
            );
            Assertions.assertEquals("Name cannot be null", exception.getMessage());
            // Verify original name is unchanged
            Assertions.assertEquals(VALID_NAME, task.getName());
        }
        
        @Test
        @DisplayName("Should reject empty name update")
        public void testSetNameEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> task.setName("")
            );
            Assertions.assertEquals("Name cannot be empty", exception.getMessage());
            // Verify original name is unchanged
            Assertions.assertEquals(VALID_NAME, task.getName());
        }
        
        @Test
        @DisplayName("Should reject too long name update")
        public void testSetNameTooLong() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> task.setName(TOO_LONG_NAME)
            );
            Assertions.assertTrue(exception.getMessage().contains("cannot be longer than 20 characters"));
            // Verify original name is unchanged
            Assertions.assertEquals(VALID_NAME, task.getName());
        }
        
        @Test
        @DisplayName("Should reject null description update")
        public void testSetDescriptionNull() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> task.setDescription(null)
            );
            Assertions.assertEquals("Description cannot be null", exception.getMessage());
            // Verify original description is unchanged
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should reject empty description update")
        public void testSetDescriptionEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> task.setDescription("")
            );
            Assertions.assertEquals("Description cannot be empty", exception.getMessage());
            // Verify original description is unchanged
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should reject too long description update")
        public void testSetDescriptionTooLong() {
            IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, 
                () -> task.setDescription(TOO_LONG_DESCRIPTION)
            );
            Assertions.assertTrue(exception.getMessage().contains("cannot be longer than 50 characters"));
            // Verify original description is unchanged
            Assertions.assertEquals(VALID_DESCRIPTION, task.getDescription());
        }
        
        @Test
        @DisplayName("Should update name to maximum length")
        public void testSetNameMaxLength() {
            task.setName(MAX_LENGTH_NAME);
            Assertions.assertEquals(MAX_LENGTH_NAME, task.getName());
        }
        
        @Test
        @DisplayName("Should update description to maximum length")
        public void testSetDescriptionMaxLength() {
            task.setDescription(MAX_LENGTH_DESCRIPTION);
            Assertions.assertEquals(MAX_LENGTH_DESCRIPTION, task.getDescription());
        }
    }
    
    @Nested
    @DisplayName("Task ID Immutability Tests")
    class TaskIdImmutabilityTests {
        
        @Test
        @DisplayName("Task ID should remain unchanged after creation")
        public void testTaskIdImmutable() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            String originalTaskId = task.getTaskId();
            
            // Update other fields
            task.setName("New Name");
            task.setDescription("New Description");
            
            // Task ID should remain the same
            Assertions.assertEquals(originalTaskId, task.getTaskId());
        }
    }
    
    @Nested
    @DisplayName("Object Methods Tests")
    class ObjectMethodsTests {
        
        @Test
        @DisplayName("toString should return formatted string")
        public void testToString() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            String expected = String.format("Task{taskId='%s', name='%s', description='%s'}", 
                VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Assertions.assertEquals(expected, task.toString());
        }
        
        @Test
        @DisplayName("equals should return true for tasks with same ID")
        public void testEqualsWithSameId() {
            Task task1 = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Task task2 = new Task(VALID_TASK_ID, "Different Name", "Different Description");
            
            Assertions.assertEquals(task1, task2);
        }
        
        @Test
        @DisplayName("equals should return false for tasks with different IDs")
        public void testEqualsWithDifferentIds() {
            Task task1 = new Task("ID1", VALID_NAME, VALID_DESCRIPTION);
            Task task2 = new Task("ID2", VALID_NAME, VALID_DESCRIPTION);
            
            Assertions.assertNotEquals(task1, task2);
        }
        
        @Test
        @DisplayName("equals should return false when compared to null")
        public void testEqualsWithNull() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Assertions.assertNotEquals(task, null);
        }
        
        @Test
        @DisplayName("equals should return false when compared to different class")
        public void testEqualsWithDifferentClass() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            String notATask = "Not a task";
            Assertions.assertNotEquals(task, notATask);
        }
        
        @Test
        @DisplayName("equals should return true when compared to itself")
        public void testEqualsWithSelf() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Assertions.assertEquals(task, task);
        }
        
        @Test
        @DisplayName("hashCode should be consistent with equals")
        public void testHashCodeConsistency() {
            Task task1 = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Task task2 = new Task(VALID_TASK_ID, "Different Name", "Different Description");
            
            // If objects are equal, their hash codes must be equal
            if (task1.equals(task2)) {
                Assertions.assertEquals(task1.hashCode(), task2.hashCode());
            }
        }
        
        @Test
        @DisplayName("hashCode should be based on task ID")
        public void testHashCodeBasedOnTaskId() {
            Task task = new Task(VALID_TASK_ID, VALID_NAME, VALID_DESCRIPTION);
            Assertions.assertEquals(VALID_TASK_ID.hashCode(), task.hashCode());
        }
    }
}
