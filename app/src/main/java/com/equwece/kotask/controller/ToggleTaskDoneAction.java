package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.data.TaskItem.TaskStatus;
import com.equwece.kotask.view.TaskItemComponent;
import com.equwece.kotask.view.TaskList;

final public class ToggleTaskDoneAction extends AbstractAction {

    final private AppEnv appEnv;
    final private TaskItemComponent selectedItem;

    public ToggleTaskDoneAction(AppEnv appEnv, TaskItemComponent selectedItem) {
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Boolean itemIsDone = ToggleTaskDoneAction.this.selectedItem.getTaskItem()
                        .getTaskStatus() == TaskStatus.DONE;
                TaskItem taskItem = selectedItem.getTaskItem();
                TaskItem newItem = new TaskItem(
                        taskItem.getHeadLine(),
                        taskItem.getId(),
                        taskItem.getDescription(),
                        (itemIsDone ? TaskStatus.ACTIVE : TaskStatus.DONE),
                        taskItem.getCreationDate());
                ToggleTaskDoneAction.this.appEnv.getTaskController().editItem(newItem.getId(), newItem);
                return null;
            }

            @Override
            protected void done() {
                new FetchTaskListWorker(ToggleTaskDoneAction.this.appEnv.getTaskController()) {

                    @Override
                    protected void done() {
                        try {
                            TaskList taskList = appEnv.getAppWindow().getTaskList();
                            int selectedIndex = taskList.getSelectedIndex();
                            taskList.updateTaskList(get());
                            taskList.setSelectedIndex(selectedIndex);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }.execute();
            }

        }.execute();
    }

}
