package com.equwece.kotask.view;

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
