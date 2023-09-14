package com.equwece.kotask.data;

import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jdbi.v3.core.Jdbi;

final public class SqliteTagDao implements TagDao {
    final private Jdbi jdbi;

    public SqliteTagDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> tags = this.jdbi.withHandle(handle -> {
            return handle.createQuery(
                    "SELECT * FROM \"tag\"")
                    .mapTo(Tag.class)
                    .list();
        });
        return tags;
    }

    @Override
    public Optional<Tag> get(String title) {
        Optional<Tag> tag = this.jdbi.withHandle(handle -> {
            return handle.createQuery(
                    "SELECT * FROM \"tag\" WHERE title = :title")
                    .bind("title", title)
                    .mapTo(Tag.class)
                    .findOne();
        });
        return tag;
    }

    @Override
    public void create(Tag tag) {
        int tagColorRgb = tag.getColor().orElse(Color.GREEN).getRGB();
        this.jdbi.withHandle(handle -> {
            return handle.createUpdate(
                    "INSERT INTO \"tag\" (title, color) "
                            + "VALUES (:title, :color)")
                    .bind("title", tag.getTitle())
                    .bind("color", tagColorRgb)
                    .execute();
        });
    }

    @Override
    public void delete(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void edit(String title, Tag tag) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'edit'");
    }

    @Override
    public List<Tag> getTaskTags(UUID taskId) {
        List<Tag> tags = this.jdbi.withHandle(handle -> {
            return handle.select(
                    "SELECT *"
                            + " FROM tag"
                            + " INNER JOIN task_tag"
                            + "     ON tag.title = task_tag.tag_title"
                            + " INNER JOIN task"
                            + "     ON task.id == task_tag.task_id"
                            + " WHERE task.id = :task_id;")
                    .bind("task_id", taskId)
                    .mapTo(Tag.class)
                    .list();
        });
        return tags;
    }

    @Override
    public void addTagToTask(TaskItem task, Tag tag) {
        this.jdbi.withHandle(handle -> {
            return handle.createUpdate(
                    "INSERT INTO \"task_tag\" (task_id, tag_title) "
                            + "VALUES (:task_id, :tag_title)")
                    .bind("task_id", task.getId())
                    .bind("tag_title", tag.getTitle())
                    .execute();
        });
    }

}
