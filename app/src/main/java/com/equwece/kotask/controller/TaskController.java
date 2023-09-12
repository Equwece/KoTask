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

    public void createItem(String headLine, String description) {
        TaskItem newItem = new TaskItem(headLine, UUID.randomUUID(), Optional.of(description));
        this.appEnv.getTaskDao().create(newItem);
    }
}
