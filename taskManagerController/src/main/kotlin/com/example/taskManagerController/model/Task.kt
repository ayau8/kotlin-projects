package com.example.taskManagerController.model;

import java.time.LocalDate
import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    var title: String,
    var date: LocalDate,
    var description: String?,
    var isCompleted: Boolean = false
)