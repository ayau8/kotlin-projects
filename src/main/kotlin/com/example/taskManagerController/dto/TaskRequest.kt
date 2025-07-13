package com.example.taskManagerController.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID

data class TaskRequest (
    @field:NotBlank(message = "Task title cannot be empty")
    @field:Size(max = 255, message = "Task title cannot exceed 255 characters")
    val title: String,

    @field:NotNull(message = "Task date cannot be null")
    val date: LocalDate,

    val description: String?,
    val isCompleted: Boolean = false,

    @field:NotNull(message = "User ID is required for task creation")
    val userId: UUID
)