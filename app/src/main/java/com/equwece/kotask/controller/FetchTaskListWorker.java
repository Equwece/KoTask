package com.equwece.kotask.controller;

import java.util.List;

import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskItem;

public class FetchTaskListWorker extends SwingWorker<List<TaskItem>, Void> {
    final private TaskController taskController;

    public FetchTaskListWorker(TaskController taskController) {
        super();
        this.taskController = taskController;
    }

    @Override
    protected List<TaskItem> doInBackground() throws Exception {
        return taskController.getAllItems();
    }
}
