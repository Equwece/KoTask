package com.equwece.kotask.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.equwece.kotask.view.TaskItem;

final public class TestTaskDao implements TaskDao {

    @Override
    public List<TaskItem> getAll() {
        List<TaskItem> resultItems = Arrays.asList(
                new TaskItem("Task 1"),
                new TaskItem("Task 2"),
                new TaskItem("Task 3"));
        return resultItems;
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
