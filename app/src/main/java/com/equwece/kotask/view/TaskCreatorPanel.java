package com.equwece.kotask.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.SaveTaskAction;
import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.data.TaskItem.TaskStatus;

import net.miginfocom.swing.MigLayout;

public class TaskCreatorPanel extends JDialog {


    abstract class TaskCreatorKeyAdapter extends KeyAdapter {
        private final JTextField headLineInput;
        private final JTextArea descriptionInput;

        public TaskCreatorKeyAdapter(JTextField headLineInput, JTextArea descriptionInput) {
            this.headLineInput = headLineInput;
            this.descriptionInput = descriptionInput;
        }

        @Override
        public void keyTyped(KeyEvent event) {
            switch (event.getKeyChar()) {
                case '\n': {
                    buildTaskAndSave(headLineInput, descriptionInput, null);
                    break;
                }
                case KeyEvent.VK_ESCAPE: {
                    TaskCreatorPanel.this.dispose();
                    break;
                }
            }

        }
    }

    final private AppEnv appEnv;
    final private UUID ascendantTaskId;

    public TaskCreatorPanel(String frameName, AppEnv appEnv) {
        this(frameName, appEnv, TaskController.ROOT_TASK_ID);
    }

    public TaskCreatorPanel(String frameName, AppEnv appEnv, UUID rootTaskId) {
        super(appEnv.getAppWindow());
        this.appEnv = appEnv;
        this.ascendantTaskId = rootTaskId;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle(frameName);
        this.setLocationRelativeTo(getParent());

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
        JTextField headLineInput = new JTextField();
        JTextArea descriptionInput = new JTextArea();
        descriptionInput.setRows(7);
        JScrollPane scrollPane = new JScrollPane(descriptionInput);

        headLineInput.addKeyListener(
                new TaskCreatorKeyAdapter(headLineInput, descriptionInput) {
                });

        descriptionInput.addKeyListener(
                new TaskCreatorKeyAdapter(headLineInput, descriptionInput) {
                });

        JPanel buttonContainer = new JPanel();
        JButton saveTask = new JButton("Save");
        saveTask.addActionListener(e -> {
            buildTaskAndSave(headLineInput, descriptionInput, e);
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

    private void buildTaskAndSave(JTextField headLineInput, JTextArea descriptionInput, ActionEvent e) {
        if (headLineInput.getText() != null) {
            TaskItem newItem = new TaskItem(
                    headLineInput.getText(),
                    UUID.randomUUID(),
                    Optional.of(descriptionInput.getText()),
                    TaskStatus.ACTIVE,
                    LocalDateTime.now());
            new SaveTaskAction(appEnv, newItem, this.ascendantTaskId).actionPerformed(e);
            TaskCreatorPanel.this.dispose();
        }
    }

}
