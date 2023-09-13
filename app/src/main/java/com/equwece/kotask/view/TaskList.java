package com.equwece.kotask.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopup(e);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopup(e);
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            TaskItemComponent selectedItem = this.getSelectedComponent(e);
                            new TaskEditorPanel("Edit task", appEnv, selectedItem.getTaskItem())
                                    .setupPanelWidgets()
                                    .run();
                        }
                    }

                    public TaskItemComponent getSelectedComponent(MouseEvent e) {
                        TaskItemComponent selectedComponent = TaskList.this.getModel()
                                .getElementAt(TaskList.this.locationToIndex(e.getPoint()));
                        return selectedComponent;
                    }

                    private void showPopup(MouseEvent e) {
                        TaskContextMenu popup = new TaskContextMenu(appEnv, this.getSelectedComponent(e));
                        popup.show(e.getComponent(),
                                e.getX(), e.getY());
                    }
                });
    }

    public void updateTaskList(List<TaskItem> newTaskList) {
        sortTaskListByDate(newTaskList);
        TaskItemComponent[] newTaskComponents = this.constructTaskComponents(newTaskList);
        this.setListData(newTaskComponents);
    }

    public void sortTaskListByDate(List<TaskItem> taskList) {
        taskList.sort(Comparator.comparing(TaskItem::getCreationDate));
        Collections.reverse(taskList);
    }

    public TaskItemComponent[] constructTaskComponents(List<TaskItem> items) {
        TaskItemComponent[] taskItems = new TaskItemComponent[items.size()];
        for (int i = 0; i < items.size(); i++) {
            taskItems[i] = new TaskItemComponent(items.get(i));
        }
        return taskItems;
    }
}
