package com.equwece.kotask.data;

import java.util.Optional;
import java.util.UUID;

final public class TaskItem {
    final private String headLine;
    final private UUID id;
    final private Optional<String> description;

    public TaskItem(String headLine, UUID id, Optional<String> description) {
        this.headLine = headLine;
        this.id = id;
        this.description = description;
    }

    public TaskItem(String headLine) {
        this.headLine = headLine;
        this.description = Optional.empty();
        this.id = UUID.randomUUID();
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
}
