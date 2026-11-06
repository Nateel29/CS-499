# Grand Strand Systems Mobile App Services

## Project Overview
This project implements the core service classes for Grand Strand Systems' mobile application, providing in-memory data management for contacts, tasks, and appointments. The implementation follows strict requirements for field validation, data integrity, and service operations.

## Author
Allan Torres

## Version History
- **Version 1.0** (August 5, 2025) - Initial implementation
  - Created Contact, Task, and Appointment domain classes with validation
  - Implemented ContactService, TaskService, and AppointmentService with CRUD operations
  - Developed comprehensive JUnit tests for all classes
  
- **Version 1.1** (August 6, 2025) - Code review and verification
  - Verified all implementations meet requirements
  - Confirmed test coverage exceeds 80% for all Java files
  - Prepared final submission package

## Code Functionality

### Domain Classes

#### Contact Class
- **Fields**: contactId (immutable), firstName, lastName, phone, address
- **Validation**:
  - contactId: non-null, max 10 chars
  - firstName, lastName: non-null, max 10 chars
  - phone: non-null, exactly 10 digits
  - address: non-null, max 30 chars
- **Features**: Field validation, immutable ID, updatable fields with validation

#### Task Class
- **Fields**: taskId (immutable), name, description
- **Validation**:
  - taskId: non-null, max 10 chars
  - name: non-null, max 20 chars
  - description: non-null, max 50 chars
- **Features**: Field validation, immutable ID, updatable fields with validation

#### Appointment Class
- **Fields**: appoId (immutable), appoDate, appoDescription
- **Validation**:
  - appoId: non-null, max 10 chars
  - appoDate: non-null, not in the past
  - appoDescription: non-null, max 50 chars
- **Features**: Field validation, immutable ID, date validation, updatable fields with validation

### Service Classes

#### ContactService
- **Operations**: Add, delete, update (firstName, lastName, phone, address), retrieve
- **Features**: Unique ID validation, in-memory storage using HashMap

#### TaskService
- **Operations**: Add, delete, update (name, description), retrieve, search
- **Features**: Unique ID validation, in-memory storage using HashMap, search by name/description

#### AppointmentService
- **Operations**: Add, delete, update (date, description), retrieve, search by date
- **Features**: Unique ID validation, in-memory storage using HashMap, date-based retrieval

## Usage

### Creating Objects
```java
// Create a contact
Contact contact = new Contact("C123", "John", "Doe", "1234567890", "123 Main St");

// Create a task
Task task = new Task("T456", "Project Plan", "Create project plan for new feature");

// Create an appointment
Date futureDate = getFutureDate(); // Helper method to get future date
Appointment appointment = new Appointment("A789", "Client Meeting", futureDate);
```

### Using Services
```java
// Contact Service
ContactService contactService = new ContactService();
contactService.addContact("C123", "John", "Doe", "1234567890", "123 Main St");
contactService.updateContactFirstName("C123", "Jane");
contactService.deleteContact("C123");

// Task Service
TaskService taskService = new TaskService();
taskService.addTask("T456", "Project Plan", "Create project plan for new feature");
taskService.updateTaskName("T456", "Updated Plan");
taskService.deleteTask("T456");

// Appointment Service
AppointmentService appointmentService = new AppointmentService();
Date futureDate = getFutureDate(); // Helper method to get future date
appointmentService.addAppointment("A789", "Client Meeting", futureDate);
appointmentService.updateAppointmentDescription("A789", "Follow-up Meeting");
appointmentService.deleteAppointment("A789");
```

## Testing
All classes are thoroughly tested using JUnit 5, with test coverage exceeding 90% for all Java files. Tests cover:
- Field validation (null, empty, length constraints)
- Service operations (add, delete, update, retrieve)
- Edge cases and exception handling
- Data integrity during multiple operations

## Instructor Feedback
Instructor feedback noted that some test validation might be excessive, particularly duplicate validation testing at both the domain object and service levels. This feedback was noted but not incorporated into the current submission as:

1. The tests are already passing (73/73 tests)
2. The implementation meets all requirements
3. No specific changes were recommended for the current submission
4. Feedback for the Appointment implementation is not yet available

Future implementations will consider:
- Reducing redundant testing between domain objects and services
- Using fewer assertions per test or using assertAll()

## Notes
- This implementation uses in-memory data structures (HashMap) for storage
- No database or UI components are included
- All validation is performed at both the domain object and service levels
- The project is designed for verification through JUnit tests
