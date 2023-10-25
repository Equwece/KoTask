package com.equwece.kotask.controller;

import java.util.ArrayList;
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
            List<TaskItem> subtasks = this.getTaskDao().getTaskSubtasks(task.getId());
            items.set(i, (task.setTaskTags(tags).setTaskSubtasks(subtasks)));
        }
        return items;
    }

    public void createItem(TaskItem item) {
        TaskItem root = this.getTaskDao().getRootTask();
        this.getTaskDao().create(item);
        this.getTaskDao().addSubtask(root.getId(), item.getId());
        for (Tag tag : item.getTags()) {
            if (this.getTagDao().get(tag.getTitle()).isEmpty()) {
                this.getTagDao().create(tag);
            }
            this.getTagDao().addTagToTask(item, tag);
        }
    }

    public void editItem(UUID itemId, TaskItem newItem) {
        this.getTaskDao().edit(itemId, newItem);
        List<String> currentTaskTagsTitles = new ArrayList<>(this
                .getTagDao()
                .getTaskTags(itemId)
                .stream()
                .map(
                        t -> t.getTitle())
                .toList());

        for (Tag tag : newItem.getTags()) {
            if (currentTaskTagsTitles.contains(tag.getTitle())) {
                currentTaskTagsTitles.remove(tag.getTitle());
            } else {
                if (this.getTagDao().get(tag.getTitle()).isEmpty()) {
                    this.getTagDao().create(tag);
                }
                this.getTagDao().addTagToTask(newItem, tag);
            }
        }

        for (String tagTitle : currentTaskTagsTitles) {
            if (this.getTagDao().getTagTasks(tagTitle).size() <= 1) {
                this.getTagDao().delete(tagTitle);
            }
            this.getTagDao().deleteTaskTagRelation(tagTitle, newItem.getId());
        }
    }

    public void deleteItem(UUID itemId) {
        this.getTaskDao().delete(itemId);
    }
}
