package com.equwece.kotask.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.equwece.kotask.AppEnv;

import net.miginfocom.swing.MigLayout;

final public class ToolBar extends JPanel {

    final private AppEnv appEnv;

    public ToolBar(AppEnv appEnv) {
        super();
        this.appEnv = appEnv;
        MigLayout toolLayout = new MigLayout("");
        this.setLayout(toolLayout);

        JButton createTaskButton = new JButton("Create task");
        createTaskButton.addActionListener(event -> {
            new TaskCreatorPanel("Create task", appEnv);
        });
        this.add(createTaskButton);
    }
}
