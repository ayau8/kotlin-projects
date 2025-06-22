package com.example.taskManagerController.service;

import com.example.taskManagerController.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.UUID;

public class TaskServiceTest {

    private lateinit var taskService: TaskService

    private val ID_CAMPING = UUID.fromString("e72f3a8c-9d1e-4f0a-b1c2-3d4e5f6a7b8c")
    private val ID_WORK = UUID.fromString("1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d")
    private val ID_COOK = UUID.fromString("2a3b4c5d-6e7f-8a9b-0c1d-2e3f4a5b6c7d")

    @BeforeEach
    fun setUp() {
        taskService = TaskService();
    }

    // --- READ (getAllTasks) ---
    @Test
    fun testGetAllTasks_returnsAllInitialTasks() {
        val allTasks = taskService.getAllTasks();

        assertNotNull(allTasks, "List of tasks should not be null");
        assertEquals(5, allTasks.size, "Initial task lists should be 5 tasks");
        assertEquals("Hiking", allTasks.first().title, "First task title should be hiking");
    }

    // --- READ (getTaskById) ---
    @Test
    fun testGetTaskById_found_returnsCorrectTask() {
        val result = taskService.getTaskById(ID_CAMPING);

        assertNotNull(result, "Task with ID $ID_CAMPING should be found");
        assertEquals("Camping", result?.title, "Task title should be Camping");
        assertEquals(LocalDate.of(2025, 6, 6), result?.date, "Date should be 6 June 2025")
    }

    @Test
    fun testGetTaskById_notFound_returnsEmptyOptional() {
        val nonExistentTaskId = UUID.randomUUID();
        val result = taskService.getTaskById(nonExistentTaskId);

        assertNull(result, "Task should not be found, should return null")
    }

//     --- CREATE (addTask) ---
    @Test
    fun testAddTask_success_increasesSizeAndIsRetrievable() {
        val initialSize = taskService.getAllTasks().size;
        val fakeId = UUID.randomUUID()
        val newTask = Task(fakeId,"Groceries", LocalDate.of(2025, 6, 15), "Tokyu Store", false);
        val createdTask = taskService.addTask(newTask);

        assertEquals(initialSize + 1, taskService.getAllTasks().size, "Task list size should increase by 1");
        assertEquals("Groceries", createdTask.title);

        val foundTask = taskService.getTaskById(createdTask.id);
        assertNotNull(foundTask, "Newly added task should be retrievable by its ID");
        assertEquals(createdTask.id, foundTask?.id, "Retrieved task ID should match created task ID")
    }

    // --- UPDATE (updateTask) ---
    @Test
    fun testUpdateTask_success_updatesTaskAndReturnsOptional(){
        val updatedTask = Task (ID_COOK, "Cook", LocalDate.of(2025, 6, 9), "Breakfast, Lunch", false);
        val updatedResult = taskService.updateTask(ID_COOK, updatedTask);

        assertNotNull(updatedResult, "Updated task should be present");
        assertEquals("Breakfast, Lunch", updatedResult?.description, "Description should be updated")
    }

    @Test
    fun testUpdateTask_notFound_returnsEmptyOptional() {
        val nonExistentTaskId = UUID.randomUUID();
        val updatedTask = Task(nonExistentTaskId, "Non Existent", LocalDate.of(2025, 6, 10), "Random", false);

        val result = taskService.updateTask(nonExistentTaskId, updatedTask);

        assertNull(result, "Updating a non-existent task should return an empty Optional");
    }

    // --- DELETE (deleteTask) ---
    @Test
    fun testDeleteTask_idFound_removesTaskAndReturnsTrue(){
        val initialSize = taskService.getAllTasks().size;

        val deleted = taskService.deleteTask(ID_WORK);

        assertTrue(deleted, "Task should be successfully deleted");
        assertEquals(initialSize - 1, taskService.getAllTasks().size, "Task list size should decrease by 1");
    }

    @Test
    fun testDeleteTask_idNotFound_returnsFalseAndKeepsSize(){
        val taskId = UUID.randomUUID();
        val initialSize = taskService.getAllTasks().size;

        val deleted = taskService.deleteTask(taskId);

        assertFalse(deleted, "Deleting a non-existent task should return false");
        assertEquals(initialSize, taskService.getAllTasks().size, "Task list size should not change when task not found");
    }
}
