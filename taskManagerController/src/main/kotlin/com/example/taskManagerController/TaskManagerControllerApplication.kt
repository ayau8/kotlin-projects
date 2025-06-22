package com.example.taskManagerController;

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication // 1
class TaskManagerControllerApplication // 2

fun main(args: Array<String>) { // 3
    SpringApplication.run(TaskManagerControllerApplication::class.java, *args) // 4
}