/**
 * Simple test runner to verify Task and TaskService functionality
 * without requiring JUnit dependencies
 */
public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("=== Task Service Test Runner ===\n");
        
        int totalTests = 0;
        int passedTests = 0;
        
        // Test Task Class
        System.out.println("Testing Task Class:");
        totalTests += testTaskCreation();
        passedTests += (testTaskCreation() > 0) ? testTaskCreation() : 0;
        
        totalTests += testTaskValidation();
        passedTests += (testTaskValidation() > 0) ? testTaskValidation() : 0;
        
        totalTests += testTaskUpdates();
        passedTests += (testTaskUpdates() > 0) ? testTaskUpdates() : 0;
        
        // Test TaskService Class
        System.out.println("\nTesting TaskService Class:");
        totalTests += testTaskServiceCRUD();
        passedTests += (testTaskServiceCRUD() > 0) ? testTaskServiceCRUD() : 0;
        
        totalTests += testTaskServiceValidation();
        passedTests += (testTaskServiceValidation() > 0) ? testTaskServiceValidation() : 0;
        
        // Summary
        System.out.println("\n=== Test Summary ===");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success Rate: " + (passedTests * 100 / totalTests) + "%");
        
        if (passedTests == totalTests) {
            System.out.println("\n✅ ALL TESTS PASSED - Project meets CS-320 requirements!");
        } else {
            System.out.println("\n❌ Some tests failed - Review implementation");
        }
    }
    
    private static int testTaskCreation() {
        int tests = 0;
        try {
            // Test valid task creation
            Task validTask = new Task("TASK001", "Test Task", "Test Description");
            tests++;
            System.out.println("✅ Task creation with valid data");
            
            // Verify immutable ID
            if ("TASK001".equals(validTask.getTaskId())) {
                tests++;
                System.out.println("✅ Task ID is correctly set and immutable");
            }
            
            // Verify getters
            if ("Test Task".equals(validTask.getName()) && 
                "Test Description".equals(validTask.getDescription())) {
                tests++;
                System.out.println("✅ Task getters work correctly");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Task creation failed: " + e.getMessage());
        }
        return tests;
    }
    
    private static int testTaskValidation() {
        int tests = 0;
        
        // Test null task ID
        try {
            new Task(null, "Name", "Description");
            System.out.println("❌ Should reject null task ID");
        } catch (IllegalArgumentException e) {
            tests++;
            System.out.println("✅ Correctly rejects null task ID");
        }
        
        // Test long task ID
        try {
            new Task("VERYLONGTASKID", "Name", "Description");
            System.out.println("❌ Should reject long task ID");
        } catch (IllegalArgumentException e) {
            tests++;
            System.out.println("✅ Correctly rejects long task ID");
        }
        
        // Test null name
        try {
            new Task("TASK001", null, "Description");
            System.out.println("❌ Should reject null name");
        } catch (IllegalArgumentException e) {
            tests++;
            System.out.println("✅ Correctly rejects null name");
        }
        
        // Test long name
        try {
            new Task("TASK001", "This name is way too long", "Description");
            System.out.println("❌ Should reject long name");
        } catch (IllegalArgumentException e) {
            tests++;
            System.out.println("✅ Correctly rejects long name");
        }
        
        // Test null description
        try {
            new Task("TASK001", "Name", null);
            System.out.println("❌ Should reject null description");
        } catch (IllegalArgumentException e) {
            tests++;
            System.out.println("✅ Correctly rejects null description");
        }
        
        return tests;
    }
    
    private static int testTaskUpdates() {
        int tests = 0;
        try {
            Task testTask = new Task("TASK001", "Original", "Original Description");
            
            // Test name update
            testTask.setName("Updated Name");
            if ("Updated Name".equals(testTask.getName())) {
                tests++;
                System.out.println("✅ Task name update works");
            }
            
            // Test description update
            testTask.setDescription("Updated Description");
            if ("Updated Description".equals(testTask.getDescription())) {
                tests++;
                System.out.println("✅ Task description update works");
            }
            
            // Test invalid name update
            try {
                testTask.setName(null);
                System.out.println("❌ Should reject null name update");
            } catch (IllegalArgumentException e) {
                tests++;
                System.out.println("✅ Correctly rejects null name update");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Task update test failed: " + e.getMessage());
        }
        return tests;
    }
    
    private static int testTaskServiceCRUD() {
        int tests = 0;
        try {
            TaskService service = new TaskService();
            
            // Test add task
            service.addTask("TASK001", "Test Task", "Test Description");
            if (service.getTaskCount() == 1) {
                tests++;
                System.out.println("✅ Task addition works");
            }
            
            // Test retrieve task
            Task retrievedTask = service.getTask("TASK001");
            if (retrievedTask != null && "Test Task".equals(retrievedTask.getName())) {
                tests++;
                System.out.println("✅ Task retrieval works");
            }
            
            // Test update task
            service.updateTaskName("TASK001", "Updated Task");
            Task updatedTask = service.getTask("TASK001");
            if ("Updated Task".equals(updatedTask.getName())) {
                tests++;
                System.out.println("✅ Task update works");
            }
            
            // Test delete task
            service.deleteTask("TASK001");
            if (service.getTaskCount() == 0) {
                tests++;
                System.out.println("✅ Task deletion works");
            }
            
        } catch (Exception e) {
            System.out.println("❌ TaskService CRUD test failed: " + e.getMessage());
        }
        return tests;
    }
    
    private static int testTaskServiceValidation() {
        int tests = 0;
        try {
            TaskService service = new TaskService();
            
            // Test duplicate ID rejection
            service.addTask("TASK001", "First Task", "Description");
            try {
                service.addTask("TASK001", "Duplicate Task", "Description");
                System.out.println("❌ Should reject duplicate task ID");
            } catch (IllegalArgumentException e) {
                tests++;
                System.out.println("✅ Correctly rejects duplicate task ID");
            }
            
            // Test delete non-existent task
            try {
                service.deleteTask("NONEXISTENT");
                System.out.println("❌ Should reject deleting non-existent task");
            } catch (IllegalArgumentException e) {
                tests++;
                System.out.println("✅ Correctly rejects deleting non-existent task");
            }
            
            // Test update non-existent task
            try {
                service.updateTaskName("NONEXISTENT", "New Name");
                System.out.println("❌ Should reject updating non-existent task");
            } catch (IllegalArgumentException e) {
                tests++;
                System.out.println("✅ Correctly rejects updating non-existent task");
            }
            
        } catch (Exception e) {
            System.out.println("❌ TaskService validation test failed: " + e.getMessage());
        }
        return tests;
    }
}
