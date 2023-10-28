package com.equwece.kotask.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        MigLayout toolLayout = new MigLayout("fillx", "[][]", "[]");
        this.setLayout(toolLayout);

        JPanel leftMenu = new JPanel(new MigLayout("", "", "0[]0"));
        JButton createTaskButton = new JButton("\uFF0B");
        createTaskButton.addActionListener(new OpenTaskCreatorAction(appEnv));
        leftMenu.add(createTaskButton);

        JButton settingsButton = new JButton(new String("\u22EE"));
        leftMenu.add(settingsButton);

        this.add(leftMenu, "push");

        JButton closeAppButton = new JButton("\u2715");
        closeAppButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        ToolBar.this.appEnv.getAppWindow().dispose();
                    }
                });
        this.add(closeAppButton);
        unfocus(this);
    }

    public void unfocus(Component component) {
        component.setFocusable(false);
        if (component instanceof Container) {
            for (Component c : ((Container) component).getComponents()) {
                unfocus(c);
            }
        }
    }
}
