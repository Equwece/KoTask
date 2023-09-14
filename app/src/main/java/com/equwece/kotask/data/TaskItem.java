package com.equwece.kotask.data;

import java.time.LocalDateTime;
import java.util.List;
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
    final private Optional<List<Tag>> tags;

    public TaskItem(String headLine, UUID id, Optional<String> description, TaskStatus taskStatus,
            LocalDateTime creationDate, Optional<List<Tag>> tags) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = taskStatus;
        this.creationDate = creationDate;
        this.tags = tags;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
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

    public Optional<List<Tag>> getTags() {
        return tags;
    }

    public TaskItem setTaskTags(List<Tag> newTags) {
        return new TaskItem(
                headLine, id, description, taskStatus, creationDate, Optional.of(newTags));

    }
}
