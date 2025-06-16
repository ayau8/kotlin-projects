package com.example.taskManagerController.service;
import com.example.taskManagerController.model.Task;
import jakarta.annotation.Nonnull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
        tasks.add(new Task(1L, "Hiking", LocalDate.of(2025, 6, 5), "Okutama", true));
        tasks.add(new Task(2L, "Camping", LocalDate.of(2025, 6, 6), "Yamanashi", true));
        tasks.add(new Task(3L, "Snowboarding", LocalDate.of(2025, 6, 7), "Nozawa", false));
        tasks.add(new Task(4L, "Work", LocalDate.of(2025, 6, 8), "Day and PT", false));
        tasks.add(new Task(5L, "Cook", LocalDate.of(2025, 6, 9), "Breakfast, Lunch, Dinner", false));
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Optional<Task> getTaskById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID for retrieval cannot be null.");
        }
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    public Task addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task to add cannot be null.");
        }
        task.setId(generateNextId());
        tasks.add(task);
        return task;
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID for update cannot be null.");
        }
        if (updatedTask == null) {
            throw new IllegalArgumentException("Updated task object cannot be null.");
        }

        return getTaskById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDate(updatedTask.getDate());
            task.setDescription(updatedTask.getDescription());
            task.setIsCompleted(updatedTask.getIsCompleted());
            return task;
        });
    }

    public boolean deleteTask(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID for deletion cannot be null.");
        }
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    private Long generateNextId() {
        return tasks.stream().mapToLong(Task::getId).max().orElse(0L) + 1;
    }
}
