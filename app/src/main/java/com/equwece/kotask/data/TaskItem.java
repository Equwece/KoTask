package com.equwece.kotask.data;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

final public class TaskItem {
    public enum TaskStatus {
        DONE, ACTIVE
    }

    final private String headLine;
    final private UUID id;
    final private Optional<String> description;
    final private TaskStatus taskStatus;
    final private LocalDateTime creationDate;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public TaskItem(String headLine, UUID id, Optional<String> description, TaskStatus taskStatus,
            LocalDateTime creationDate) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = taskStatus;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public String getHeadLine() {
        return headLine;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}
