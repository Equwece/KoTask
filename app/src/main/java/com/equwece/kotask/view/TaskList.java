package com.equwece.kotask.view;

import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskDao;

public class TaskList extends JList<TaskItemComponent> {
    final private TaskController taskController;
    final private TaskDao taskDao;

    public TaskList(TaskDao taskDao) {
        super();
        this.taskDao = taskDao;
        this.taskController = new TaskController(this.taskDao);
        this.setCellRenderer(new TaskListComponentRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.setLayoutOrientation(JList.VERTICAL);
        this.setVisibleRowCount(-1);
        this.setListData(this.getTaskItems());
    }

    public TaskItemComponent[] getTaskItems() {
        List<TaskItem> items = this.taskController.getAllItems();
        TaskItemComponent[] taskItems = new TaskItemComponent[items.size()];
        for (int i = 0; i < items.size(); i++) {
            taskItems[i] = new TaskItemComponent(items.get(i));
        }
        return taskItems;
    }
}
