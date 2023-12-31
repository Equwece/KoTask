package com.equwece.kotask.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jdbi.v3.core.Jdbi;

final public class SqliteTaskDao implements TaskDao {
    final private Jdbi jdbi;

    public SqliteTaskDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<TaskItem> getAll() {
        List<TaskItem> tasks = this.jdbi.withHandle(handle -> {
            return handle.createQuery(
                    "SELECT * FROM \"task\"")
                    .mapTo(TaskItem.class)
                    .list();
        });
        return tasks;
    }

    @Override
    public Optional<TaskItem> get(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public UUID create(TaskItem item) {
        this.jdbi.withHandle(handle -> {
            return handle.createUpdate(
                    "INSERT INTO \"task\" (id, head_line, description, status, creation_date) "
                            + "VALUES (:id, :head_line, :description, :status, :creation_date)")
                    .bind("id", item.getId())
                    .bind("head_line", item.getHeadLine())
                    .bind("description", item.getDescription().orElse(null))
                    .bind("status", item.getTaskStatus())
                    .bind("creation_date", item.getCreationDate().toString())
                    .execute();
        });
        return item.getId();
    }

    @Override
    public void delete(UUID id) {
        this.jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM \"task\" WHERE id = :id")
                    .bind("id", id).execute();
        });
    }

    @Override
    public void edit(UUID id, TaskItem item) {
        this.jdbi.withHandle(handle -> {
            return handle.createUpdate(
                    "UPDATE \"task\" "
                            + "SET head_line = :head_line, description = :description, status = :status"
                            + ", creation_date = :creation_date"
                            + " WHERE id = :id")
                    .bind("head_line", item.getHeadLine())
                    .bind("description", item.getDescription().orElse(null))
                    .bind("id", id)
                    .bind("status", item.getTaskStatus())
                    .bind("creation_date", item.getCreationDate().toString())
                    .execute();
        });
    }

}
