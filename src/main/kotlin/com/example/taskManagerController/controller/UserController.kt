package com.example.taskManagerController.controller

import com.example.taskManagerController.entity.UserEntity
import com.example.taskManagerController.model.User
import com.example.taskManagerController.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUserWithTasks(@RequestBody req: User): ResponseEntity<UserEntity> {
        val user = userService.createUserWithTasks(
            req.username,
            req.password,
            req.roles,
            req.tasks
        )

        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.id)
            .toUri()

        return ResponseEntity.created(location)
            .body(user)
    }
}