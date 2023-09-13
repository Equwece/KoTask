package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;
import java.util.Optional;
import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.view.TaskEditorPanel;

final public class UpdateTaskAction extends AbstractAction {

    final private AppEnv appEnv;
    final private JTextField headLineInput;
    final private JTextArea descriptionInput;
    final private TaskEditorPanel taskEditorPanel;

    public UpdateTaskAction(AppEnv appEnv, JTextField headLineInput, JTextArea descriptionInput,
            TaskEditorPanel taskEditorPanel) {
        this.appEnv = appEnv;
        this.headLineInput = headLineInput;
        this.descriptionInput = descriptionInput;
        this.taskEditorPanel = taskEditorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        taskEditorPanel.dispose();
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                TaskItem updatedItem = new TaskItem(
                        headLineInput.getText(),
                        taskEditorPanel.getSelectedItem().getId(),
                        Optional.of(descriptionInput.getText()),
                        taskEditorPanel.getSelectedItem().getTaskStatus(),
                        taskEditorPanel.getSelectedItem().getCreationDate());
                appEnv.getTaskController().editItem(updatedItem.getId(), updatedItem);
                return null;
            }

            @Override
            protected void done() {
                new FetchTaskListWorker(appEnv.getTaskController()) {
                    @Override
                    protected void done() {
                        try {
                            taskEditorPanel
                                    .getAppEnv().getAppWindow().getTaskList().updateTaskList(get());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        taskEditorPanel.dispose();
                    }
                }.execute();
            }
        }.execute();
    }

}
