package com.equwece.kotask.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<TaskItem> firstLevelTasks = this.getTaskDao()
                .getTaskSubtasks(UUID.fromString("00000000-0000-0000-0000-000000000000")).stream()
                .map(this::finishTaskItem).collect(Collectors.toList());
        ;
        return firstLevelTasks;
    }

    public TaskItem finishTaskItem(TaskItem task) {
        List<Tag> tags = this.getTagDao().getTaskTags(task.getId());
        List<TaskItem> subtasks = this.getTaskDao().getTaskSubtasks(task.getId()).stream().map(this::finishTaskItem)
                .collect(Collectors.toList());
        return task.setTaskTags(tags).setTaskSubtasks(subtasks);
    }

    public void createItem(TaskItem item) {
        this.createItem(item, UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }

    public void createItem(TaskItem item, UUID ascendantId) {
        this.getTaskDao().create(item);
        this.getTaskDao().addSubtask(ascendantId, item.getId());
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
        }
    }

    public void deleteItem(UUID itemId) {
        this.getTaskDao().delete(itemId);
    }
}
