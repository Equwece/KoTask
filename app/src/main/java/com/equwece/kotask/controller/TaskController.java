package com.equwece.kotask.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskItem;

final public class TaskController {
    final private AppEnv appEnv;

    public TaskController(AppEnv appEnv) {
        this.appEnv = appEnv;
    }

    public List<TaskItem> getAllItems() {
        List<TaskItem> items = appEnv.getTaskDao().getAll();
        return items;
    }

    public void createItem(TaskItem item) {
        this.appEnv.getTaskDao().create(item);
    }

    public void editItem(UUID itemId, TaskItem newItem) {
        this.appEnv.getTaskDao().edit(itemId, newItem);
    }

    public void deleteItem(UUID itemId) {
        this.appEnv.getTaskDao().delete(itemId);
    }
}
