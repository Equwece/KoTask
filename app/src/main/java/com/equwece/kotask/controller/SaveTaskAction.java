package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskItem;

final public class SaveTaskAction extends AbstractAction {

    final private AppEnv appEnv;
    final private TaskItem taskItem;
    final private UUID ascendantTaskId;

    public SaveTaskAction(AppEnv appEnv, TaskItem taskItem) {
        this.appEnv = appEnv;
        this.taskItem = taskItem;
        this.ascendantTaskId = TaskController.ROOT_TASK_ID;
    }

    public SaveTaskAction(AppEnv appEnv, TaskItem taskItem, UUID rootTaskId) {
        this.appEnv = appEnv;
        this.taskItem = taskItem;
        this.ascendantTaskId = rootTaskId;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                appEnv.getTaskController().createItem(SaveTaskAction.this.taskItem, SaveTaskAction.this.ascendantTaskId);
                return null;
            }

            @Override
            protected void done() {
                new FetchTaskListWorker(appEnv.getTaskController()) {
                    @Override
                    protected void done() {
                        new FetchTaskListWorker(appEnv.getTaskController()) {
                            @Override
                            protected void done() {
                                try {
                                    appEnv.getAppWindow().getTaskList().updateTaskList(get());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute();
                    }
                }.execute();
            }
        }.execute();
    }
}
