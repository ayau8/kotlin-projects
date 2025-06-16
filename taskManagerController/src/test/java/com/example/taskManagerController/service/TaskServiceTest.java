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
    void testGetAllTasks_returnsAllInitialTasks() {
        List<Task> allTasks = taskservice.getAllTasks();

        assertNotNull(allTasks, "List of tasks should not be null");
        assertEquals(5, allTasks.size(), "Initial task lists should be 5 tasks");
        assertEquals("Hiking", allTasks.getFirst().getTitle(), "First task title should be hiking");
    }

    // --- READ (getTaskById) ---
    @Test
    void testGetTaskById_found_returnsCorrectTask() {
        Long taskId = 2L;

        Optional<Task> result = taskservice.getTaskById(taskId);

        assertTrue(result.isPresent(), "Task with ID 2 should be found");
        assertEquals("Camping", result.get().getTitle(), "Task title should be Camping");
        assertEquals(LocalDate.of(2025, 6, 6), result.get().getDate(), "Date should be 6 June 2025");
    }

    @Test
    void testGetTaskById_notFound_returnsEmptyOptional() {
        Long nonExistentTaskId = 99L;

        Optional<Task> result = taskservice.getTaskById(nonExistentTaskId);

        assertFalse(result.isPresent(), "Task with ID 99 should not be found");
    }

    @Test
    void testGetTaskById_nullId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> taskservice.getTaskById(null),
        "Calling getTaskById with null ID should throw IllegalArgumentException");
    }

    // --- CREATE (addTask) ---
    @Test
    void testAddTask_success_increasesSizeAndIsRetrievable() {
        int initialSize = taskservice.getAllTasks().size();
        Task newTask = new Task(null, "Groceries", LocalDate.of(2025, 6, 15), "Tokyu Store", false);

        Task createdTask = taskservice.addTask(newTask);

        assertEquals(initialSize + 1, taskservice.getAllTasks().size(), "Task list size should increase by 1");
        assertEquals("Groceries", createdTask.getTitle());

        Optional<Task> foundTask = taskservice.getTaskById(createdTask.getId());
        assertTrue(foundTask.isPresent(), "Newly added task should be retrievable by its ID");
        assertEquals(createdTask.getId(), foundTask.get().getId(), "Retrieved task ID should match created task ID");
    }

    @Test
    void testAddTask_nullTask_throwsIllegalArgumentException()  {
        assertThrows(IllegalArgumentException.class, () -> taskservice.addTask(null),
        "Adding a null task should throw IllegalArgumentException");
    }

    // --- UPDATE (updateTask) ---
    @Test
    void testUpdateTask_success_updatesTaskAndReturnsOptional(){
        Long taskId = 5L;
        Task updatedTask = new Task(5L, "Cook", LocalDate.of(2025, 6, 9), "Breakfast, Lunch", false);

        Optional<Task> updatedResult = taskservice.updateTask(taskId, updatedTask);

        assertTrue(updatedResult.isPresent(), "Updated task should be present");
        assertEquals("Breakfast, Lunch", updatedResult.get().getDescription(), "Description should be updated");
    }

    void testUpdateTask_notFound_returnsEmptyOptional() {
        Long nonExistentTaskId = 99L;
        Task updatedTask = new Task(nonExistentTaskId, "Non Existent", LocalDate.of(2025, 6, 10), "Random", false);

        Optional<Task> result = taskservice.updateTask(nonExistentTaskId, updatedTask);

        assertFalse(result.isPresent(), "Updating a non-existent task should return an empty Optional");
    }

    @Test
    void testUpdateTask_nullId_throwsIllegalArgumentException() {
        Task updatedTask = new Task(null, "Cook", LocalDate.of(2025, 6, 9), "Breakfast, Lunch", false);
        assertThrows(IllegalArgumentException.class, () -> taskservice.updateTask(null, updatedTask),
        "Calling updateTask with null ID should throw IllegalArgumentException");
    }

    // --- DELETE (deleteTask) ---
    @Test
    void testDeleteTask_idFound_removesTaskAndReturnsTrue(){
        Long taskId = 4L;
        int initialSize = taskservice.getAllTasks().size();

        boolean deleted = taskservice.deleteTask(taskId);

        assertTrue(deleted, "Task with ID 4 should be successfully deleted");
        assertEquals(initialSize - 1, taskservice.getAllTasks().size(), "Task list size should decrease by 1");
    }

    @Test
    void testDeleteTask_idNotFound_returnsFalseAndKeepsSize(){
        Long taskId = 99L;
        int initialSize = taskservice.getAllTasks().size();

        boolean deleted = taskservice.deleteTask(taskId);

        assertFalse(deleted, "Deleting a non-existent task should return false");
        assertEquals(initialSize, taskservice.getAllTasks().size(), "Task list size should not change when task not found");
    }

    @Test
    void testDeleteTask_nullId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> taskservice.deleteTask(null),
        "Calling deleteTask with null ID should throw IllegalArgumentException");
    }
}
