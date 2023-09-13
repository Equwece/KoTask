package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.view.TaskEditorPanel;
import com.equwece.kotask.view.TaskItemComponent;

final public class DeleteTaskAction extends AbstractAction {

    final private AppEnv appEnv;
    final private TaskItemComponent selectedItem;

    public DeleteTaskAction(AppEnv appEnv, TaskItemComponent selectedItem) {
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                DeleteTaskAction.this.appEnv.getTaskController().deleteItem(selectedItem.getTaskItem().getId());
                return null;
            }

            @Override
            protected void done() {
                new FetchTaskListWorker(DeleteTaskAction.this.appEnv.getTaskController()) {
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

}
