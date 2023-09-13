package com.equwece.kotask.view;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.FetchTaskListWorker;
import com.equwece.kotask.controller.TaskController;
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
        TaskController taskController = this.getAppEnv().getTaskController();
        JTextField headLineInput = new JTextField();
        headLineInput.setText(this.getSelectedItem().getHeadLine());

        JTextArea descriptionInput = new JTextArea();
        descriptionInput.setText(this.getSelectedItem().getDescription().orElse(""));
        descriptionInput.setRows(7);

        JScrollPane scrollPane = new JScrollPane(descriptionInput);

        JPanel buttonContainer = new JPanel();
        JButton saveTask = new JButton("Save");
        saveTask.addActionListener(event -> {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    TaskItem updatedItem = new TaskItem(
                            headLineInput.getText(),
                            TaskEditorPanel.this.getSelectedItem().getId(),
                            Optional.of(descriptionInput.getText()),
                            TaskEditorPanel.this.getSelectedItem().getTaskStatus(),
                            TaskEditorPanel.this.getSelectedItem().getCreationDate());
                    taskController.editItem(updatedItem.getId(), updatedItem);
                    return null;
                }

                @Override
                protected void done() {
                    new FetchTaskListWorker(taskController) {
                        @Override
                        protected void done() {
                            try {
                                TaskEditorPanel.this
                                        .getAppEnv().getAppWindow().getTaskList().updateTaskList(get());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            TaskEditorPanel.this.dispose();
                        }
                    }.execute();
                }
            }.execute();

        });
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
