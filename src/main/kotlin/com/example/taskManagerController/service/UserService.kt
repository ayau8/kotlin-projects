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
    fun createUser(username: String, password: String, roles: String): UserEntity {
        val user = UserEntity(username = username, password = password, roles = roles)
        return userRepository.save(user)
    }

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
