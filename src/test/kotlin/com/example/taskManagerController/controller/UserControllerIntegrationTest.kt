package com.example.taskManagerController.controller

import com.example.taskManagerController.model.User
import com.example.taskManagerController.model.Task
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper.registerModule(JavaTimeModule())
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `POST_api_users_with_tasks - should create user with tasks`() {
        val newUser = User(
            username = "alvinyau",
            password = "password123",
            roles = "USER",
            tasks = listOf(
                Task(title = "Task", date = LocalDate.of(2025, 7, 7), description = "Description", isCompleted = false)
            )
        )
        val newUserJson = objectMapper.writeValueAsString(newUser)

        mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newUserJson))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.username").value("alvinyau"))
            .andExpect(jsonPath("$.tasks.length()").value(1))
            .andExpect(jsonPath("$.tasks[0].title").value("Task"))
    }
}
