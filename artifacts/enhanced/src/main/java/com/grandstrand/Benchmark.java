package com.grandstrand;

import com.grandstrand.domain.Appointment;
import com.grandstrand.domain.Task;
import com.grandstrand.repository.AppointmentRepository;
import com.grandstrand.repository.TaskRepository;
import com.grandstrand.repository.InMemoryAppointmentRepository;
import com.grandstrand.repository.InMemoryTaskRepository;
import com.grandstrand.repository.IndexedAppointmentRepository;
import com.grandstrand.repository.IndexedTaskRepository;
import com.grandstrand.service.AppointmentService;
import com.grandstrand.service.TaskService;
import java.util.Date;
import java.util.List;

/**
 * Benchmark harness comparing linear scan vs indexed query performance.
 * Demonstrates the algorithmic improvement from Milestone Three.
 */
public class Benchmark {
    private static final int[] DATA_SIZES = {100, 1000, 10000};
    private static final int WARMUP_ITERATIONS = 3;
    private static final int TEST_ITERATIONS = 10;

    public static void main(String[] args) {
        System.out.println("=== Grand Strand Systems Algorithm Benchmark ===");
        System.out.println("Comparing linear scan (O(n)) vs indexed query (O(log n) / O(1))");
        System.out.println();

        benchmarkAppointmentDateRange();
        System.out.println();
        benchmarkTaskNameLookup();
    }

    private static void benchmarkAppointmentDateRange() {
        System.out.println("--- Appointment Date Range Query ---");
        System.out.println("Size\tLinear (ns)\tIndexed (ns)\tSpeedup");

        for (int size : DATA_SIZES) {
            // Setup linear repository
            AppointmentRepository linearRepo = new InMemoryAppointmentRepository();
            AppointmentService linearService = new AppointmentService(linearRepo);
            populateAppointments(linearService, size);

            // Setup indexed repository
            AppointmentRepository indexedRepo = new IndexedAppointmentRepository();
            AppointmentService indexedService = new AppointmentService(indexedRepo);
            populateAppointments(indexedService, size);

            long now = System.currentTimeMillis();
            Date startDate = new Date(now + 86400000 * 2); // day after tomorrow
            Date endDate = new Date(now + 86400000 * 10); // 10 days out

            // Warmup
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                linearService.getAppointmentsByDateRange(startDate, endDate);
                indexedService.getAppointmentsByDateRange(startDate, endDate);
            }

            // Measure linear
            long linearStart = System.nanoTime();
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                linearService.getAppointmentsByDateRange(startDate, endDate);
            }
            long linearTime = (System.nanoTime() - linearStart) / TEST_ITERATIONS;

            // Measure indexed
            long indexedStart = System.nanoTime();
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                indexedService.getAppointmentsByDateRange(startDate, endDate);
            }
            long indexedTime = (System.nanoTime() - indexedStart) / TEST_ITERATIONS;

            double speedup = (double) linearTime / indexedTime;
            System.out.printf("%d\t%d\t\t%d\t\t%.1fx%n", size, linearTime, indexedTime, speedup);
        }
    }

    private static void benchmarkTaskNameLookup() {
        System.out.println("--- Task Name Lookup ---");
        System.out.println("Size\tLinear (ns)\tIndexed (ns)\tSpeedup");

        for (int size : DATA_SIZES) {
            // Setup linear repository
            TaskRepository linearRepo = new InMemoryTaskRepository();
            TaskService linearService = new TaskService(linearRepo);
            populateTasks(linearService, size);

            // Setup indexed repository
            TaskRepository indexedRepo = new IndexedTaskRepository();
            TaskService indexedService = new TaskService(indexedRepo);
            populateTasks(indexedService, size);

            String searchName = "Task" + (size / 2); // middle element

            // Warmup
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                linearService.getTasksByName(searchName);
                indexedService.getTasksByName(searchName);
            }

            // Measure linear
            long linearStart = System.nanoTime();
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                linearService.getTasksByName(searchName);
            }
            long linearTime = (System.nanoTime() - linearStart) / TEST_ITERATIONS;

            // Measure indexed
            long indexedStart = System.nanoTime();
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                indexedService.getTasksByName(searchName);
            }
            long indexedTime = (System.nanoTime() - indexedStart) / TEST_ITERATIONS;

            double speedup = (double) linearTime / indexedTime;
            System.out.printf("%d\t%d\t\t%d\t\t%.1fx%n", size, linearTime, indexedTime, speedup);
        }
    }

    private static void populateAppointments(AppointmentService service, int count) {
        long now = System.currentTimeMillis();
        Date baseDate = new Date(now + 86400000 * 2); // start day after tomorrow
        // Spread appointments over 7 days max to stay within query window
        long maxOffset = 86400000 * 7; // 7 days in milliseconds
        long interval = maxOffset / Math.max(count, 1);
        for (int i = 0; i < count; i++) {
            Date date = new Date(baseDate.getTime() + (i * interval));
            service.addAppointment("A" + i, "Appointment " + i, date);
        }
    }

    private static void populateTasks(TaskService service, int count) {
        for (int i = 0; i < count; i++) {
            service.addTask("T" + i, "Task" + i, "Description for task " + i);
        }
    }
}
