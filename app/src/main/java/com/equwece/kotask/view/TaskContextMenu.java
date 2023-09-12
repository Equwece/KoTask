package com.equwece.kotask.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.FetchTaskListWorker;
import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.data.TaskItem.TaskStatus;

public class TaskContextMenu extends JPopupMenu {
    private final AppEnv appEnv;
    private final TaskItemComponent selectedItem;

    public TaskContextMenu(AppEnv appEnv, TaskItemComponent selectedItem) {
        super();
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;
        Boolean itemIsDone = selectedItem.getTaskItem().getTaskStatus() == TaskStatus.DONE;

        TaskController taskController = new TaskController(appEnv);

        JMenuItem toggleDone = new JMenuItem(String.format("Mark as %s",
                itemIsDone ? "active" : "done"));
        toggleDone.addActionListener(event -> {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    TaskItem newItem = new TaskItem(
                            selectedItem.getTaskItem().getHeadLine(),
                            selectedItem.getTaskItem().getId(),
                            selectedItem.getTaskItem().getDescription(),
                            itemIsDone ? TaskStatus.ACTIVE : TaskStatus.DONE);
                    taskController.editItem(newItem.getId(), newItem);
                    return null;
                }

                @Override
                protected void done() {
                    new FetchTaskListWorker(taskController) {

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
        });
        this.add(toggleDone);

        JMenuItem editItem = new JMenuItem("Edit task");
        editItem.addActionListener(event -> {
            new TaskEditorPanel("Edit task", appEnv, selectedItem.getTaskItem())
                    .setupPanelWidgets().run();
        });
        this.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete task");
        deleteItem.addActionListener(event -> {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    taskController.deleteItem(selectedItem.getTaskItem().getId());
                    return null;
                }

                @Override
                protected void done() {
                    new FetchTaskListWorker(taskController) {
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
        });
        this.add(deleteItem);

    }

    public AppEnv getAppEnv() {
        return appEnv;
    }

    public TaskItemComponent getSelectedItem() {
        return selectedItem;
    }

}
