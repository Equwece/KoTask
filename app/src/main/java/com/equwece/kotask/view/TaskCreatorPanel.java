package com.equwece.kotask.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.SaveTaskAction;

import net.miginfocom.swing.MigLayout;

public class TaskCreatorPanel extends JFrame {

    private final class TaskCreatorKeyAdapter extends KeyAdapter {
        private final JTextField headLineInput;
        private final JTextArea descriptionInput;

        private TaskCreatorKeyAdapter(JTextField headLineInput, JTextArea descriptionInput) {
            this.headLineInput = headLineInput;
            this.descriptionInput = descriptionInput;
        }

        @Override
        public void keyTyped(KeyEvent event) {
            switch (event.getKeyChar()) {
                case '\n': {
                    new SaveTaskAction(appEnv, headLineInput, descriptionInput, TaskCreatorPanel.this)
                            .actionPerformed(null);
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
        JTextField headLineInput = new JTextField();
        JTextArea descriptionInput = new JTextArea();
        descriptionInput.setRows(7);
        JScrollPane scrollPane = new JScrollPane(descriptionInput);

        headLineInput.addKeyListener(
                new TaskCreatorKeyAdapter(headLineInput, descriptionInput));

        descriptionInput.addKeyListener(
                new TaskCreatorKeyAdapter(headLineInput, descriptionInput));

        JPanel buttonContainer = new JPanel();
        JButton saveTask = new JButton("Save");
        saveTask.addActionListener(new SaveTaskAction(appEnv, headLineInput, descriptionInput, this));
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
