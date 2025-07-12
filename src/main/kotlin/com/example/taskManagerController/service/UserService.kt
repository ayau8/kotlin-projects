package com.example.taskManagerController.service

import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import com.example.taskManagerController.repository.UserRepository
import com.example.taskManagerController.entity.UserEntity
import com.example.taskManagerController.entity.TaskEntity
import com.example.taskManagerController.model.Task

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    /**
     * Creates a new user with the specified username, password, and roles, and persists it.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param roles The roles assigned to the new user.
     * @return The persisted UserEntity representing the created user.
     */
    fun createUser(username: String, password: String, roles: String): UserEntity {
        val user = UserEntity(username = username, password = password, roles = roles)
        return userRepository.save(user)
    }

    /**
     * Creates a new user with the specified username, password, and roles, and associates the user with an initial list of tasks.
     *
     * Each task in the provided list is converted into a task entity linked to the new user. The user and all associated tasks are persisted in a single transaction.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param roles The roles assigned to the new user.
     * @param initialTasks The list of tasks to associate with the new user upon creation.
     * @return The persisted user entity with associated tasks.
     */
    fun createUserWithTasks( username: String, password: String, roles: String, initialTasks: List<Task>): UserEntity {
        val user = UserEntity(username = username, password = password, roles = roles)

        initialTasks.forEach { t ->
            val task = TaskEntity(
                title = t.title,
                date = t.date,
                description = t.description,
                isCompleted = t.isCompleted,
                user = user
            )
        user.tasks.add(task)
    }
    return userRepository.save(user)
    }
}
