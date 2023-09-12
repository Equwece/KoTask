package com.equwece.kotask.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.FetchTaskListWorker;
import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskItem;

import net.miginfocom.swing.MigLayout;

public class TaskCreatorPanel extends JFrame {

    final private AppEnv appEnv;

    public TaskCreatorPanel(String frameName, AppEnv appEnv) {
        super(frameName);
        this.appEnv = appEnv;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPane = new JPanel();

        MigLayout currentLayout = new MigLayout("fillx");
        contentPane.setLayout(currentLayout);

        this.setContentPane(contentPane);
        this.setLocationByPlatform(true);
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
    }

    public TaskCreatorPanel run() {
        this.setVisible(true);
        return this;
    }

    public AppEnv getAppEnv() {
        return appEnv;
    }

    public TaskCreatorPanel setupPanelWidgets() {
        TaskController taskController = new TaskController(appEnv);
        JTextField headLineInput = new JTextField();
        JTextArea descriptionInput = new JTextArea();
        descriptionInput.setRows(7);
        JScrollPane scrollPane = new JScrollPane(descriptionInput);

        JPanel buttonContainer = new JPanel();
        JButton saveTask = new JButton("Save");
        saveTask.addActionListener(event -> {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    taskController.createItem(headLineInput.getText(), descriptionInput.getText());
                    return null;
                }

                @Override
                protected void done() {
                    new SwingWorker<List<TaskItem>, Void>() {
                        @Override
                        protected List<TaskItem> doInBackground() throws Exception {
                            return taskController.getAllItems();
                        }

                        @Override
                        protected void done() {
                            new FetchTaskListWorker(taskController) {
                                @Override
                                protected void done() {
                                    try {
                                        appEnv.getAppWindow().getTaskList().updateTaskList(get());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    TaskCreatorPanel.this.dispose();
                                }
                            }.execute();
                        }
                    }.execute();
                }
            }.execute();

        });
        JButton cancelCreation = new JButton("Cancel");
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
