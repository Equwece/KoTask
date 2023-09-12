package com.equwece.kotask.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskItem;

public class TaskList extends JList<TaskItemComponent> {
    final private TaskController taskController;
    final private AppEnv appEnv;

    public TaskList(AppEnv appEnv) {
        super();
        this.appEnv = appEnv;
        this.taskController = new TaskController(this.appEnv);
        this.setCellRenderer(new TaskListComponentRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.setLayoutOrientation(JList.VERTICAL);
        this.setVisibleRowCount(-1);

        this.addMouseListener(
                new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopup(e);
                        }
                    }

                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopup(e);
                        }
                    }

                    private void showPopup(MouseEvent e) {
                        TaskItemComponent selectedComponent = TaskList.this.getModel()
                                .getElementAt(TaskList.this.locationToIndex(e.getPoint()));
                        TaskContextMenu popup = new TaskContextMenu(appEnv, selectedComponent);
                        popup.show(e.getComponent(),
                                e.getX(), e.getY());
                    }
                });
    }

    public void updateTaskList(List<TaskItem> newTaskList) {
        TaskItemComponent[] newTaskComponents = this.constructTaskComponents(newTaskList);
        this.setListData(newTaskComponents);
    }

    public TaskItemComponent[] constructTaskComponents(List<TaskItem> items) {
        TaskItemComponent[] taskItems = new TaskItemComponent[items.size()];
        for (int i = 0; i < items.size(); i++) {
            taskItems[i] = new TaskItemComponent(items.get(i));
        }
        return taskItems;
    }
}
