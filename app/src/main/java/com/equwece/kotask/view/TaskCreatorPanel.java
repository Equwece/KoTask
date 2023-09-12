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
        TaskController taskController = new TaskController(appEnv);

        MigLayout currentLayout = new MigLayout("fillx");
        contentPane.setLayout(currentLayout);

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

        contentPane.add(headLineInput, "growx, wrap");
        contentPane.add(scrollPane, "grow, wrap");
        contentPane.add(buttonContainer);

        this.getContentPane().add(contentPane);
        this.setLocationByPlatform(true);
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        // headLineInput.requestFocus();
    }

}
