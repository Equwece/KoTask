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
import com.equwece.kotask.view.TaskList;

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
        if (headLineInput.getText() != null && !headLineInput.getText().isEmpty()) {
            taskEditorPanel.dispose();
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    TaskItem selectedItem = taskEditorPanel.getSelectedItem();
                    TaskItem updatedItem = new TaskItem(
                            headLineInput.getText(),
                            selectedItem.getId(),
                            Optional.of(descriptionInput.getText()),
                            selectedItem.getTaskStatus(),
                            selectedItem.getCreationDate());
                    appEnv.getTaskController().editItem(updatedItem.getId(), updatedItem);
                    return null;
                }

                @Override
                protected void done() {
                    new FetchTaskListWorker(appEnv.getTaskController()) {
                        @Override
                        protected void done() {
                            try {
                                TaskList taskList = taskEditorPanel.getAppEnv().getAppWindow().getTaskList();
                                int selectedIndex = taskList.getSelectedIndex();
                                taskList.updateTaskList(get());
                                taskList.setSelectedIndex(selectedIndex);
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

}
