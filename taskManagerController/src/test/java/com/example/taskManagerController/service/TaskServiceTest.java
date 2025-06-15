package com.example.taskManagerController.service;

import com.example.taskManagerController.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public class TaskServiceTest {

    private TaskService taskservice;

    @BeforeEach
    void setUp() {
        taskservice = new TaskService();
    }

    // --- READ (getAllTasks) ---
    @Test
    void testGetAllTasks() {
        List<Task> allTasks = taskservice.getAllTasks();

        assertNotNull(allTasks, "List of tasks should not be null");
        assertEquals(5, allTasks.size(), "Initial task lists should be 5 tasks");
        assertEquals("Hiking", allTasks.getFirst().getTitle(), "First task title should be hiking");
    }

    // --- READ (getTaskById) ---
    @Test
    void testGetTaskById_found() {
        Long taskId = 2L;

        Optional<Task> result = taskservice.getTaskById(taskId);

        assertTrue(result.isPresent(), "Task with ID 2 should be found");
        assertEquals("Camping", result.get().getTitle(), "Task title should be Camping");
        assertEquals(LocalDate.of(2025, 6, 6), result.get().getDate(), "Date should be 6 June 2025");
    }

    @Test
    void testGetTaskById_notFound() {
        Long nonExistentTaskId = 99L;

        Optional<Task> result = taskservice.getTaskById(nonExistentTaskId);

        assertFalse(result.isPresent(), "Task with ID 99 should not be found");
    }

    // --- CREATE (addTask) ---
    @Test
    void testAddTask_successAndRetrievable() {
        int initialSize = taskservice.getAllTasks().size();
        Task newTask = new Task(null, "Groceries", LocalDate.of(2025, 6, 15), "Tokyu Store", false);

        Task createdTask = taskservice.addTask(newTask);

        assertEquals(initialSize + 1, taskservice.getAllTasks().size(), "Task list size should increase by 1");
        assertEquals("Groceries", createdTask.getTitle());

        Optional<Task> foundTask = taskservice.getTaskById(createdTask.getId());
        assertTrue(foundTask.isPresent(), "Newly added task should be retrievable by its ID");
        assertEquals(createdTask.getId(), foundTask.get().getId(), "Retrieved task ID should match created task ID");
    }

    // --- UPDATE (updateTask) ---
    @Test
    void testUpdateTask_success(){
        Long taskId = 5L;
        Task updatedTask = new Task(5L, "Cook", LocalDate.of(2025, 6, 9), "Breakfast, Lunch", false);

        Optional<Task> updatedResult = taskservice.updateTask(taskId, updatedTask);

        assertTrue(updatedResult.isPresent(), "Updated task should be present");
        assertEquals("Breakfast, Lunch", updatedResult.get().getDescription(), "Description should be updated");
    }

    // --- DELETE (deleteTask) ---
    @Test
    void testDeleteTask_idFound(){
        Long taskId = 4L;
        int initialSize = taskservice.getAllTasks().size();

        boolean deleted = taskservice.deleteTask(taskId);

        assertTrue(deleted, "Task with ID 4 should be successfully deleted");
        assertEquals(initialSize - 1, taskservice.getAllTasks().size(), "Task list size should decrease by 1");
    }

    @Test
    void testDeleteTask_idNotFound_returnsFalse(){
        Long taskId = 99L;
        int initialSize = taskservice.getAllTasks().size();

        boolean deleted = taskservice.deleteTask(taskId);

        assertFalse(deleted, "Deleting a non-existent task should return false");
        assertEquals(initialSize, taskservice.getAllTasks().size(), "Task list size should not change when task not found");
    }
}
