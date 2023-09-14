package com.equwece.kotask.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class TaskListComponentRenderer implements ListCellRenderer<TaskItemComponent> {

    @Override
    public Component getListCellRendererComponent(JList<? extends TaskItemComponent> list, TaskItemComponent value,
            int index, boolean isSelected, boolean cellHasFocus) {
        Color bgColor;
        Color fgColor;
        if (isSelected) {
            bgColor = (Color) UIManager.get("List.selectionBackground");

            fgColor = (Color) UIManager.get("List.selectionForeground");
        } else {
            bgColor = (Color) UIManager.get("List.background");

            fgColor = (Color) UIManager.get("List.foreground");
        }
        value.setBackground(bgColor);

        for (Component comp : value.getComponents()) {
            if (comp.getName() != "taskTag") {
                comp.setForeground(fgColor);
            }
        }

        return value;
    }

}
