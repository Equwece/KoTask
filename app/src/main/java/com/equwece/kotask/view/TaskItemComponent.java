package com.equwece.kotask.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.equwece.kotask.data.Tag;
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
        this.add(Box.createRigidArea(new Dimension(50,0)));

        for (Tag tag : this.taskItem.getTags().orElse(new ArrayList<>())) {
            JLabel tagLabel = new JLabel(tag.getTitle());
            tagLabel.setName("taskTag");
            if (!tag.getColor().isEmpty()) {
                tagLabel.setForeground(tag.getColor().get());
            }
            this.add(tagLabel);
            this.add(Box.createRigidArea(new Dimension(10,0)));
        }
    }

    public TaskItem getTaskItem() {
        return taskItem;
    }
}
