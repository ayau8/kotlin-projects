package com.example.taskManagerController.service

import com.example.taskManagerController.model.Task
import com.example.taskManagerController.dto.TaskRequest
import com.example.taskManagerController.dto.TaskResponse
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class TaskService {

    private var tasks: MutableList<Task> = mutableListOf()

    init {
        resetInitialTasks()
    }

    fun resetInitialTasks() {
        tasks = mutableListOf(
        Task(UUID.fromString("c016e41b-7a32-4d2b-8a8b-1a2b3c4d5e6f"), "Hiking", LocalDate.of(2025, 6, 5), "Okutama", true),
        Task(UUID.fromString("e72f3a8c-9d1e-4f0a-b1c2-3d4e5f6a7b8c"), "Camping", LocalDate.of(2025, 6, 6), "Yamanashi", true),
        Task(UUID.fromString("f8d9b0c1-2e3f-4a5b-6c7d-8e9f0a1b2c3d"), "Snowboarding", LocalDate.of(2025, 6, 7), "Nozawa", false),
        Task(UUID.fromString("1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d"), "Work", LocalDate.of(2025, 6, 8), "Day and PT", false),
        Task(UUID.fromString("2a3b4c5d-6e7f-8a9b-0c1d-2e3f4a5b6c7d"), "Cook", LocalDate.of(2025, 6, 9), "Breakfast, Lunch, Dinner", false)
        )
    }

    fun getAllTasks(): List<Task> {
        return tasks.toList()
    }

    fun getTaskById(id: UUID): Task? {
        return tasks.firstOrNull { it.id == id }
    }

    fun addTask(request: TaskRequest): TaskResponse {
        val newTaskId = UUID.randomUUID()

        val newTask = Task(
            id = newTaskId,
            title = request.title,
            date = request.date,
            description = request.description,
            isCompleted = request.isCompleted
        )

        require(getTaskById(newTask.id) == null) { "Generated Task ID ${newTask.id} already exists. Please try again." }

        tasks.add(newTask)

        return TaskResponse(
            id = newTask.id,
            title = newTask.title,
            date = newTask.date,
            description = newTask.description,
            isCompleted = newTask.isCompleted,
            userId = request.userId
        )
    }

    fun updateTask(id: UUID, updatedTask: Task): Task? {
        require(updatedTask.id == id) { "Task object ID (${updatedTask.id}) must match the provided ID ($id) for update." }

        return tasks.firstOrNull { it.id == id }?.let { existingTask ->
            existingTask.title = updatedTask.title
            existingTask.date = updatedTask.date
            existingTask.description = updatedTask.description
            existingTask.isCompleted = updatedTask.isCompleted
            existingTask
        }
    }

    fun deleteTask(id: UUID): Boolean {
        return tasks.removeIf { it.id == id }
    }
}
