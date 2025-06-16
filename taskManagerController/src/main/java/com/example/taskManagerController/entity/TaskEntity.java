package com.example.taskManagerController.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate date;
    private String description;
    private Boolean isCompleted;

    public TaskEntity() {}

    public TaskEntity(Long id, String title, LocalDate date, String description, Boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
