package com.example.taskManagerController.controller

import com.example.taskManagerController.model.Task
import com.example.taskManagerController.service.TaskService
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

import java.time.LocalDate
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TaskManagerControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var taskService: TaskService

    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    private val ID_HIKING = UUID.fromString("c016e41b-7a32-4d2b-8a8b-1a2b3c4d5e6f")
    private val ID_CAMPING = UUID.fromString("e72f3a8c-9d1e-4f0a-b1c2-3d4e5f6a7b8c")
    private val ID_SNOWBOARDING = UUID.fromString("f8d9b0c1-2e3f-4a5b-6c7d-8e9f0a1b2c3d")
    private val ID_WORK = UUID.fromString("1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d")
    private val ID_COOK = UUID.fromString("2a3b4c5d-6e7f-8a9b-0c1d-2e3f4a5b6c7d")

    @BeforeEach
    fun setup() {
        taskService.resetInitialTasks()
    }

    // --- GET All Tasks ---
    @Test
    fun `GET_api_tasks - should return all tasks`() {
        mockMvc.perform(get("/api/tasks"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(5))
            .andExpect(jsonPath("$[0].title").value("Hiking"))
            .andExpect(jsonPath("$[0].id").value(ID_HIKING.toString()))
    }

    // --- GET Task By ID ---
    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `GET_api_tasks_id - should return task by ID if found`() {
        mockMvc.perform(get("/api/tasks/{id}", ID_CAMPING))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ID_CAMPING.toString()))
            .andExpect(jsonPath("$.title").value("Camping"))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `GET_api_tasks_id - should return 404 if task not found`() {
        val nonExistentId = UUID.randomUUID()
        mockMvc.perform(get("/api/tasks/{id}", nonExistentId))
            .andExpect(status().isNotFound)
    }

    // --- POST Add Task ---
    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `POST_api_tasks - should create a new task`() {
        val fakeId = UUID.randomUUID()
        val newTask = Task(fakeId, "New Integration Task", LocalDate.of(2026, 1, 1), "Test description", false)
        val newTaskJson = objectMapper.writeValueAsString(newTask)

        mockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newTaskJson))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value("New Integration Task"))
            .andExpect(jsonPath("$.isCompleted").value(false))

        mockMvc.perform(get("/api/tasks"))
            .andExpect(jsonPath("$.length()").value(6))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `POST_api_tasks - should return 400 if task has ID on creation`() {
        val taskWithExistingId = Task(ID_HIKING, "Existing ID Task", LocalDate.now(), "Desc", false)
        val taskJson = objectMapper.writeValueAsString(taskWithExistingId)

        mockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(taskJson))
            .andExpect(status().isBadRequest)
    }

    // --- PUT Update Task ---
    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `PUT_api_tasks_id - should update existing task`() {
        val taskIdToUpdate = ID_WORK
        val updatedTask = Task(taskIdToUpdate, "Updated Work Task", LocalDate.of(2025, 6, 8), "Updated description for work", true)
        val updatedTaskJson = objectMapper.writeValueAsString(updatedTask)

        mockMvc.perform(put("/api/tasks/{id}", taskIdToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedTaskJson))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(taskIdToUpdate.toString()))
            .andExpect(jsonPath("$.title").value("Updated Work Task"))
            .andExpect(jsonPath("$.isCompleted").value(true))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `PUT_api_tasks_id - should return 404 if task to update not found`() {
        val nonExistentId = UUID.randomUUID()
        val updatedTask = Task(nonExistentId, "Non Existent Update", LocalDate.now(), "Some description", false)
        val updatedTaskJson = objectMapper.writeValueAsString(updatedTask)

        mockMvc.perform(put("/api/tasks/{id}", nonExistentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedTaskJson))
            .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `PUT_api_tasks_id - should return 400 if ID in path and body do not match`() {
        val pathId = ID_WORK
        val bodyId = ID_HIKING
        val updatedTask = Task(bodyId, "Mismatched Update", LocalDate.now(), "Description", false)
        val updatedTaskJson = objectMapper.writeValueAsString(updatedTask)

        mockMvc.perform(put("/api/tasks/{id}", pathId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedTaskJson))
            .andExpect(status().isBadRequest)
    }

    // --- DELETE Task ---
    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `DELETE_api_tasks_id - should delete existing task`() {
        val taskIdToDelete = ID_SNOWBOARDING

        mockMvc.perform(delete("/api/tasks/{id}", taskIdToDelete))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/tasks/{id}", taskIdToDelete))
            .andExpect(status().isNotFound)

        mockMvc.perform(get("/api/tasks"))
            .andExpect(jsonPath("$.length()").value(4))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    fun `DELETE_api_tasks_id - should return 404 if task to delete not found`() {
        val nonExistentId = UUID.randomUUID()

        mockMvc.perform(delete("/api/tasks/{id}", nonExistentId))
            .andExpect(status().isNotFound)

        mockMvc.perform(get("/api/tasks"))
            .andExpect(jsonPath("$.length()").value(5))
    }
}