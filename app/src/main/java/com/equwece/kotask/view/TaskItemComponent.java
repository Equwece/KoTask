package com.equwece.kotask.view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.equwece.kotask.data.TaskItem;

public class TaskItemComponent extends JPanel {
    private final TaskItem taskItem;

    public TaskItemComponent(TaskItem taskItem) {
        super();
        this.taskItem = taskItem;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(new JLabel(taskItem.getHeadLine()));
    }

    public TaskItem getTaskItem() {
        return taskItem;
    }
}
