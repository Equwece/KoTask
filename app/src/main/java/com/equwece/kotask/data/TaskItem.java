package com.equwece.kotask.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;

final public class TaskItem {
    public enum TaskStatus {
        DONE, ACTIVE
    }

    final private String headLine;
    final private UUID id;
    final private Optional<String> description;
    final private TaskStatus taskStatus;
    final private LocalDateTime creationDate;
    final private List<Tag> tags;
    final private List<TaskItem> subtasks;

    public TaskItem(String headLine, UUID id, Optional<String> description, TaskStatus taskStatus,
            LocalDateTime creationDate, List<Tag> tags, List<TaskItem> subtasks) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = taskStatus;
        this.creationDate = creationDate;
        this.tags = tags;
        this.subtasks = subtasks;
    }

    public TaskItem(String headLine, UUID id, Optional<String> description, TaskStatus taskStatus,
            LocalDateTime creationDate, List<Tag> tags) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = taskStatus;
        this.creationDate = creationDate;
        this.tags = tags;
        this.subtasks = new ArrayList<>();
    }

    public TaskItem(String headLine, UUID id, Optional<String> description, TaskStatus taskStatus,
            LocalDateTime creationDate) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
        this.taskStatus = taskStatus;
        this.creationDate = creationDate;
        this.tags = this.parseTaskTags(description.orElse(""));
        this.subtasks = new ArrayList<>();
    }

    public List<TaskItem> getSubtasks() {
        return subtasks;
    }

    public List<Tag> parseTaskTags(String description) {
        Pattern pattern = Pattern.compile("\\+[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(description);

        List<Tag> listMatches = new ArrayList<Tag>();

        while (matcher.find()) {
            Tag newTag = new Tag(Optional.of(Color.GREEN), matcher.group().replace("+", ""));
            listMatches.add(newTag);
        }
        return listMatches;
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

    public List<Tag> getTags() {
        return tags;
    }

    public TaskItem setTaskTags(List<Tag> newTags) {
        return new TaskItem(
                headLine, id, description, taskStatus, creationDate, newTags);

    }

    public TaskItem setTaskSubtasks(List<TaskItem> newSubtasks) {
        return new TaskItem(
                headLine, id, description, taskStatus, creationDate, tags, newSubtasks);

    }
}
