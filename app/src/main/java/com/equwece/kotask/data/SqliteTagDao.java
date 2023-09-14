package com.equwece.kotask.data;

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
    public Optional<Tag> get(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public UUID create(Tag tag) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void delete(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void edit(UUID id, Tag tag) {
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

}
