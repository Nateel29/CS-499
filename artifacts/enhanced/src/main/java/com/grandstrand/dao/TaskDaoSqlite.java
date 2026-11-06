package com.grandstrand.dao;

import com.grandstrand.domain.Task;
import com.grandstrand.repository.TaskRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of TaskRepository using JDBC.
 * All queries use PreparedStatements to prevent SQL injection.
 */
public class TaskDaoSqlite implements TaskRepository {
    private final DatabaseManager dbManager;

    public TaskDaoSqlite(DatabaseManager dbManager) {
        if (dbManager == null) {
            throw new IllegalArgumentException("DatabaseManager cannot be null");
        }
        this.dbManager = dbManager;
    }

    @Override
    public Task save(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        String sql = "INSERT INTO tasks (task_id, name, description) VALUES (?, ?, ?) " +
                     "ON CONFLICT(task_id) DO UPDATE SET " +
                     "name = excluded.name, description = excluded.description";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTaskId());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.executeUpdate();
            return task;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save task " + task.getTaskId(), e);
        }
    }

    @Override
    public Optional<Task> findById(String taskId) {
        String sql = "SELECT task_id, name, description FROM tasks WHERE task_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find task " + taskId, e);
        }
    }

    @Override
    public List<Task> findAll() {
        String sql = "SELECT task_id, name, description FROM tasks";
        List<Task> results = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve tasks", e);
        }
    }

    /**
     * Finds tasks by exact name using the idx_tasks_name index.
     * Replaces the Milestone Three in-memory HashMap index with a database index.
     */
    public List<Task> findByName(String name) {
        String sql = "SELECT task_id, name, description FROM tasks WHERE name = ?";
        List<Task> results = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find tasks by name", e);
        }
    }

    @Override
    public void deleteById(String taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taskId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete task " + taskId, e);
        }
    }

    @Override
    public boolean existsById(String taskId) {
        if (taskId == null) {
            return false;
        }
        String sql = "SELECT 1 FROM tasks WHERE task_id = ? LIMIT 1";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to check task existence " + taskId, e);
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM tasks";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to count tasks", e);
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM tasks";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to clear tasks", e);
        }
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        return new Task(
            rs.getString("task_id"),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
