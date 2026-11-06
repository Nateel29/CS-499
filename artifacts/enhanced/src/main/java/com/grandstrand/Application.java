package com.grandstrand;

import com.grandstrand.api.ContactController;
import com.grandstrand.api.TaskController;
import com.grandstrand.api.AppointmentController;
import com.grandstrand.repository.ContactRepository;
import com.grandstrand.repository.TaskRepository;
import com.grandstrand.repository.AppointmentRepository;
import com.grandstrand.repository.InMemoryContactRepository;
import com.grandstrand.repository.InMemoryTaskRepository;
import com.grandstrand.repository.InMemoryAppointmentRepository;
import com.grandstrand.service.ContactService;
import com.grandstrand.service.TaskService;
import com.grandstrand.service.AppointmentService;

/**
 * Main application class demonstrating the enhanced layered architecture.
 * Wires together repositories, services, and controllers using dependency injection.
 */
public class Application {
    public static void main(String[] args) {
        // Initialize repositories (in-memory implementations)
        ContactRepository contactRepository = new InMemoryContactRepository();
        TaskRepository taskRepository = new InMemoryTaskRepository();
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();

        // Initialize services with injected repositories
        ContactService contactService = new ContactService(contactRepository);
        TaskService taskService = new TaskService(taskRepository);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);

        // Initialize controllers with injected services
        ContactController contactController = new ContactController(contactService);
        TaskController taskController = new TaskController(taskService);
        AppointmentController appointmentController = new AppointmentController(appointmentService);

        // Demonstrate the architecture
        System.out.println("=== Grand Strand Systems Enhanced Architecture ===");
        System.out.println("Layered architecture initialized successfully:");
        System.out.println("  - Repository layer: In-memory implementations");
        System.out.println("  - Service layer: Business logic with dependency injection");
        System.out.println("  - API layer: REST controllers ready for HTTP endpoints");
        System.out.println("  - DTO layer: Data transfer objects for API contracts");
        System.out.println("\nReady for enhancement categories 2 (algorithms) and 3 (databases).");
    }
}
