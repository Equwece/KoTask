package com.equwece.kotask.view;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.OpenTaskCreatorAction;

import net.miginfocom.swing.MigLayout;

final public class ToolBar extends JPanel {

    final private AppEnv appEnv;

    public ToolBar(AppEnv appEnv) {
        super();
        this.appEnv = appEnv;
        MigLayout toolLayout = new MigLayout();
        this.setLayout(toolLayout);

        JButton createTaskButton = new JButton("\uFF0B");
        createTaskButton.addActionListener(new OpenTaskCreatorAction(appEnv));
        this.add(createTaskButton);

        for (Component component : this.getComponents()) {
            component.setFocusable(false);
        }
    }
}
