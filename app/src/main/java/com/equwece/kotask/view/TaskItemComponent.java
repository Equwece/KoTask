package com.equwece.kotask.view;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.data.TaskItem.TaskStatus;

public class TaskItemComponent extends JPanel {
    private final TaskItem taskItem;

    public TaskItemComponent(TaskItem taskItem) {
        super();
        this.taskItem = taskItem;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JLabel taskCheckBox = new JLabel(taskItem.getHeadLine());
        if (taskItem.getTaskStatus() == TaskStatus.DONE) {
            Font font = taskCheckBox.getFont();

            @SuppressWarnings("unchecked")
            Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();

            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

            taskCheckBox.setFont(font.deriveFont(attributes));
        }
        this.add(taskCheckBox);
    }

    public TaskItem getTaskItem() {
        return taskItem;
    }
}
