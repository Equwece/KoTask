package com.equwece.kotask.data;

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

    public TaskItem(String headLine, UUID id, Optional<String> description, TaskStatus taskStatus) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    public TaskItem(String headLine, UUID id, Optional<String> description) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = TaskStatus.ACTIVE;
    }

    public TaskItem(String headLine) {
        this.headLine = headLine;
        this.description = Optional.empty();
        this.id = UUID.randomUUID();
        this.taskStatus = TaskStatus.ACTIVE;
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
