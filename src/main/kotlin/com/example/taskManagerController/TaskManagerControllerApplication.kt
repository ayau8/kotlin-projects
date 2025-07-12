package com.example.taskManagerController

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan(basePackages = ["com.example.taskManagerController.entity"])
class TaskManagerApplication

/**
 * Application entry point that launches the Spring Boot Task Manager application.
 *
 * @param args Command-line arguments passed to the application.
 */
fun main(args: Array<String>) {
    runApplication<TaskManagerApplication>(*args)
}