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

    /**
     * Creates a new user along with associated tasks and returns the created user entity.
     *
     * Accepts a JSON request body representing a user, including username, password, roles, and tasks.
     * On successful creation, responds with HTTP 201 Created, sets the Location header to the new user's URI, and returns the created user entity in the response body.
     *
     * @param req The user data including username, password, roles, and tasks.
     * @return A ResponseEntity containing the created UserEntity and the Location header.
     */
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