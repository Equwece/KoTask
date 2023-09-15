package com.equwece.kotask.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagDao {
    List<Tag> getAll();

    Optional<Tag> get(String title);

    void create(Tag tag);

    void delete(String title);

    void edit(String title, Tag tag);

    List<Tag> getTaskTags(UUID taskId);

    void addTagToTask(TaskItem task, Tag tag);

    List<TaskItem> getTagTasks(String tagTitle);

    void deleteTaskTagRelation(String tagTitle, UUID taskId);
}
