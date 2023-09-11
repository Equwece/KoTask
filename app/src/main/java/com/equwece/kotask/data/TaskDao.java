package com.equwece.kotask.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskDao {
    List<TaskItem> getAll();

    Optional<TaskItem> get(UUID id);

    UUID create(TaskItem newItem);

    void delete(UUID id);

    void edit(UUID id, TaskItem item);
}
