package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Tasks")
data class TaskEntity(
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "task_date")
    var date: LocalDate?,

    @Column(name = "description")
    var description: String?,

    @Column(name = "is_completed")
    var isCompleted: Boolean?
)