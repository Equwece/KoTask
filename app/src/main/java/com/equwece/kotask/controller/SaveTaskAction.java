package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.data.TaskItem.TaskStatus;
import com.equwece.kotask.view.TaskCreatorPanel;

final public class SaveTaskAction extends AbstractAction {

    final private AppEnv appEnv;
    final private JTextField headLineInput;
    final private JTextArea descriptionInput;
    final private TaskCreatorPanel taskCreatorPanel;

    public SaveTaskAction(AppEnv appEnv, JTextField headLineInput, JTextArea descriptionInput,
            TaskCreatorPanel taskCreatorPanel) {
        this.appEnv = appEnv;
        this.headLineInput = headLineInput;
        this.descriptionInput = descriptionInput;
        this.taskCreatorPanel = taskCreatorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (headLineInput.getText() != null && !headLineInput.getText().isEmpty()) {
            taskCreatorPanel.dispose();
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    TaskItem newItem = new TaskItem(
                            headLineInput.getText(),
                            UUID.randomUUID(),
                            Optional.of(descriptionInput.getText()),
                            TaskStatus.ACTIVE,
                            LocalDateTime.now(),
                            Optional.empty());
                    appEnv.getTaskController().createItem(newItem);
                    return null;
                }

                @Override
                protected void done() {
                    new FetchTaskListWorker(appEnv.getTaskController()) {
                        @Override
                        protected void done() {
                            new FetchTaskListWorker(appEnv.getTaskController()) {
                                @Override
                                protected void done() {
                                    try {
                                        appEnv.getAppWindow().getTaskList().updateTaskList(get());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.execute();
                        }
                    }.execute();
                }
            }.execute();
        }
    }

}
