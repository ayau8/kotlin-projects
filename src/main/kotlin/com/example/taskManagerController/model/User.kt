package com.example.taskManagerController.model

data class User(
    val username: String,
    val password: String,
    val roles: String,
    val tasks: List<Task>
)