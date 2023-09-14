package com.equwece.kotask.controller;

import java.util.List;
import java.util.UUID;

import com.equwece.kotask.data.Tag;
import com.equwece.kotask.data.TagDao;
import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.data.TaskItem;

final public class TaskController {
    final private TaskDao taskDao;
    final private TagDao tagDao;

    public TagDao getTagDao() {
        return tagDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public TaskController(TaskDao taskDao, TagDao tagDao) {
        this.taskDao = taskDao;
        this.tagDao = tagDao;
    }

    public List<TaskItem> getAllItems() {
        List<TaskItem> items = this.getTaskDao().getAll();
        for (int i = 0; i < items.size(); i++) {
            TaskItem task = items.get(i);
            List<Tag> tags = this.getTagDao().getTaskTags(task.getId());
            items.set(i, (task.setTaskTags(tags)));
        }
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
