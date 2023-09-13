package com.equwece.kotask.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.data.TaskItem;

final public class TaskController {
    final private TaskDao taskDao;

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<TaskItem> getAllItems() {
        List<TaskItem> items = this.getTaskDao().getAll();
        return items;
    }

    public void createItem(TaskItem item) {
        this.getTaskDao().create(item);
    }

    public void editItem(UUID itemId, TaskItem newItem) {
        this.getTaskDao().edit(itemId, newItem);
    }

    public void deleteItem(UUID itemId) {
        this.getTaskDao().delete(itemId);
    }
}
