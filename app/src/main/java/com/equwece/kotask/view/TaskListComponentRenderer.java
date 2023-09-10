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
        if (isSelected) {
            Color selectedBackgroundColor = (Color) UIManager.get("List.selectionBackground");
            value.setBackground(selectedBackgroundColor);

            Color selectedForegroundColor = (Color) UIManager.get("List.selectionForeground");
            for (Component comp : value.getComponents()) {
                comp.setForeground(selectedForegroundColor);
            }
        } else {
            Color backgroundColor = (Color) UIManager.get("List.background");
            value.setBackground(backgroundColor);

            Color foregroundColor = (Color) UIManager.get("List.foreground");
            for (Component comp : value.getComponents()) {
                comp.setForeground(foregroundColor);
            }
        }

        return value;
    }

}
