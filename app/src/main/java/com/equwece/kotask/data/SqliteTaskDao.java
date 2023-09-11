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
    public UUID create(TaskItem newItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void delete(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void edit(UUID id, TaskItem item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'edit'");
    }

}
