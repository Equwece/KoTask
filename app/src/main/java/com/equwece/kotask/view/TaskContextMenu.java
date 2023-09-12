package com.equwece.kotask.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.FetchTaskListWorker;
import com.equwece.kotask.controller.TaskController;

public class TaskContextMenu extends JPopupMenu {
    private final AppEnv appEnv;
    private final TaskItemComponent selectedItem;

    public TaskContextMenu(AppEnv appEnv, TaskItemComponent selectedItem) {
        super();
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;

        TaskController taskController = new TaskController(appEnv);

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
