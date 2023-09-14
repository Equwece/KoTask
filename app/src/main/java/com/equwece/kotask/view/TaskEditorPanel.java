package com.equwece.kotask.view;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.UpdateTaskAction;
import com.equwece.kotask.data.TaskItem;

public class TaskEditorPanel extends TaskCreatorPanel {

    final private TaskItem selectedItem;

    public TaskEditorPanel(String frameName, AppEnv appEnv, TaskItem selectedItem) {
        super(frameName, appEnv);
        this.selectedItem = selectedItem;
    }

    public TaskItem getSelectedItem() {
        return this.selectedItem;
    }

    @Override
    public TaskEditorPanel setupPanelWidgets() {
        JTextField headLineInput = new JTextField();
        headLineInput.setText(this.getSelectedItem().getHeadLine());

        JTextArea descriptionInput = new JTextArea();
        descriptionInput.setText(this.getSelectedItem().getDescription().orElse(""));
        descriptionInput.setRows(7);
        JScrollPane scrollPane = new JScrollPane(descriptionInput);

        TaskCreatorPanel.TaskCreatorKeyAdapter taskEditorKeyAdapter = new TaskCreatorPanel.TaskCreatorKeyAdapter(
                headLineInput, descriptionInput) {
            @Override
            public void keyTyped(KeyEvent event) {
                switch (event.getKeyChar()) {
                    case '\n': {
                        new UpdateTaskAction(TaskEditorPanel.this.getAppEnv(), headLineInput, descriptionInput,
                                TaskEditorPanel.this)
                                .actionPerformed(null);
                        break;
                    }
                    case KeyEvent.VK_ESCAPE: {
                        TaskEditorPanel.this.dispose();
                        break;
                    }
                }
            }
        };
        headLineInput.addKeyListener(
                taskEditorKeyAdapter);

        descriptionInput.addKeyListener(
                taskEditorKeyAdapter);

        JPanel buttonContainer = new JPanel();
        JButton saveTask = new JButton("Save");
        saveTask.addActionListener(
                new UpdateTaskAction(TaskEditorPanel.this.getAppEnv(), headLineInput, descriptionInput,
                        TaskEditorPanel.this));
        JButton cancelCreation = new JButton("Close");
        cancelCreation.addActionListener(event -> {
            this.dispose();
        });
        buttonContainer.add(saveTask);
        buttonContainer.add(cancelCreation);

        this.getContentPane().add(headLineInput, "growx, wrap");
        this.getContentPane().add(scrollPane, "grow, wrap");
        this.getContentPane().add(buttonContainer);

        return this;
    }

}
