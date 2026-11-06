package com.grandstrand;

import com.grandstrand.dao.DatabaseManager;
import com.grandstrand.dao.ContactDaoSqlite;
import com.grandstrand.dao.TaskDaoSqlite;
import com.grandstrand.dao.AppointmentDaoSqlite;
import com.grandstrand.service.ContactService;
import com.grandstrand.service.TaskService;
import com.grandstrand.service.AppointmentService;
import java.util.Date;

/**
 * Demonstrates the SQLite persistence layer from Milestone Four.
 * Proves data survives across application runs — the key improvement
 * over the original in-memory-only artifact.
 */
public class PersistenceDemo {
    private static final String DB_PATH = "grandstrand.db";

    public static void main(String[] args) {
        System.out.println("=== Grand Strand Systems Persistence Demo ===\n");

        // Initialize database
        DatabaseManager dbManager = new DatabaseManager(DB_PATH);
        dbManager.initializeSchema();
        System.out.println("Database initialized: " + DB_PATH);

        // Wire services with SQLite DAOs (same service code, different repository)
        ContactService contactService = new ContactService(new ContactDaoSqlite(dbManager));
        TaskService taskService = new TaskService(new TaskDaoSqlite(dbManager));
        AppointmentService appointmentService = new AppointmentService(new AppointmentDaoSqlite(dbManager));

        // Add data only if not already present (proves persistence on re-run)
        if (!contactService.contactExists("C001")) {
            contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
            taskService.addTask("T001", "Buy groceries", "Milk, bread, eggs");
            Date futureDate = new Date(System.currentTimeMillis() + 86400000 * 3);
            appointmentService.addAppointment("A001", "Doctor visit", futureDate);
            System.out.println("Added: 1 contact, 1 task, 1 appointment");
        } else {
            System.out.println("Data already exists from previous run — persistence confirmed!");
        }

        // Read back counts
        System.out.println("Contacts in DB: " + contactService.getContactCount());
        System.out.println("Tasks in DB: " + taskService.getTaskCount());
        System.out.println("Appointments in DB: " + appointmentService.getAppointmentCount());

        // Demonstrate indexed queries work against the database
        System.out.println("\nTask name lookup (DB index): " +
            taskService.getTasksByName("Buy groceries").size() + " result(s)");
        Date rangeEnd = new Date(System.currentTimeMillis() + 86400000 * 10);
        System.out.println("Appointment range query (DB index): " +
            appointmentService.getAppointmentsByDateRange(new Date(), rangeEnd).size() + " result(s)");

        System.out.println("\nData persisted to " + DB_PATH);
        System.out.println("Restart the application and the data will still be there —");
        System.out.println("unlike the original in-memory implementation.");
    }
}
