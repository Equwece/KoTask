package com.equwece.kotask.view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.equwece.kotask.data.TaskItem;

public class TaskItemComponent extends JPanel {

    public TaskItemComponent(TaskItem taskItem) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(new JLabel(taskItem.getHeadLine()));
    }
}
