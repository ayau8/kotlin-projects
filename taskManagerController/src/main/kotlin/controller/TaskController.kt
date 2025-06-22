package controller;

import model.Task;
import service.TaskService;
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun getAllTasks(): List<Task> {
        return taskService.getAllTasks()
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: UUID): ResponseEntity<Task> {
        return taskService.getTaskById(id)
            ?.let { task -> ResponseEntity.ok(task) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createTask(@RequestBody task: Task): Task {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: UUID, @RequestBody updatedTask: Task): ResponseEntity<Task> {
        return taskService.updateTask(id, updatedTask)
            ?.let { task -> ResponseEntity.ok(task) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = taskService.deleteTask(id)
        return if (deleted) ResponseEntity.ok().build() else ResponseEntity.notFound().build()
    }
}