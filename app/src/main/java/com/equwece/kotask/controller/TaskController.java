package com.equwece.kotask.controller;

import java.util.List;

import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.view.TaskItem;

final public class TaskController {
    final private TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<TaskItem> getAllItems() {
        List<TaskItem> items = taskDao.getAll();
        return items;
    }
}
