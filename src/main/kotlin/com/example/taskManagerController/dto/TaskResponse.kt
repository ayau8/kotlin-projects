package com.example.taskManagerController.dto

import java.time.LocalDate
import java.util.UUID

data class TaskResponse(
    val id: UUID,
    val title: String,
    val date: LocalDate,
    val description: String?,
    val isCompleted: Boolean,
    val userId: UUID
)