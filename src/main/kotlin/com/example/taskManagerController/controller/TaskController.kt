package com.example.taskManagerController.controller

import com.example.taskManagerController.model.Task
import com.example.taskManagerController.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import java.util.UUID

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    /**
     * Retrieves a list of all tasks.
     *
     * @return A list containing all Task entities.
     */
    @GetMapping
    fun getAllTasks(): List<Task> {
        return taskService.getAllTasks()
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * Returns HTTP 200 with the task if found, or HTTP 404 if no task exists for the given ID.
     *
     * @param id The UUID of the task to retrieve.
     * @return A ResponseEntity containing the task if found, or a 404 Not Found response if not.
     */
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: UUID): ResponseEntity<Task> {
        return taskService.getTaskById(id)
            ?.let { task -> ResponseEntity.ok(task) }
            ?: ResponseEntity.notFound().build()
    }

    /**
     * Creates a new task with the provided details.
     *
     * Accepts a `Task` object in the request body and attempts to add it to the system. Returns HTTP 201 Created with the created task if successful, or HTTP 400 Bad Request if the input is invalid.
     *
     * @param task The task to be created.
     * @return A ResponseEntity containing the created task and HTTP status, or HTTP 400 if the input is invalid.
     */
    @PostMapping
    fun createTask(@RequestBody task: Task): ResponseEntity<Task?> {
        return try {
            val createdTask = taskService.addTask(task)
            ResponseEntity.status(HttpStatus.CREATED).body(createdTask)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    /**
     * Updates an existing task identified by its UUID with the provided task data.
     *
     * Returns HTTP 200 OK with the updated task if successful, HTTP 404 Not Found if the task does not exist,
     * or HTTP 400 Bad Request if the input is invalid.
     *
     * @param id The UUID of the task to update.
     * @param updatedTask The new data for the task.
     * @return A ResponseEntity containing the updated task or an appropriate HTTP status code.
     */
    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: UUID, @RequestBody updatedTask: Task): ResponseEntity<Task> {
        return try {
            taskService.updateTask(id, updatedTask)
                ?.let { task -> ResponseEntity.ok(task) }
                ?: ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    /**
     * Deletes a task by its unique identifier.
     *
     * Returns HTTP 204 No Content if the task was successfully deleted, or HTTP 404 Not Found if no task exists for the given ID.
     *
     * @param id The UUID of the task to delete.
     * @return A ResponseEntity indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = taskService.deleteTask(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }
}