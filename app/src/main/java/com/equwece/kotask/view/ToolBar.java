package com.equwece.kotask.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.OpenTaskCreatorAction;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

final public class ToolBar extends JPanel {

    final private AppEnv appEnv;

    public ToolBar(AppEnv appEnv) {
        super();
        this.appEnv = appEnv;
        MigLayout toolLayout = new MigLayout("fillx", "[][][]", "[]");
        this.setLayout(toolLayout);

        JPanel leftMenu = new JPanel(new MigLayout("fillx", "[]", "0[]0"));
        JButton createTaskButton = new JButton("\uFF0B");
        createTaskButton.addActionListener(new OpenTaskCreatorAction(appEnv));
        leftMenu.add(createTaskButton);
        JButton settingsButton = new JButton(new String("\u22EE"));
        leftMenu.add(settingsButton);
        CC leftConstaints = new CC();
        leftConstaints.alignX("left");
        this.add(leftMenu, leftConstaints);

        JPanel rightMenu = new JPanel(new MigLayout("fillx", "[]", "0[]0"));
        JButton closeAppButton = new JButton("\u2715");
        closeAppButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        ToolBar.this.appEnv.getAppWindow().dispose();
                    }
                });
        rightMenu.add(closeAppButton);
        CC componentConstraints = new CC();
        componentConstraints.alignX("right").spanX();
        this.add(rightMenu, componentConstraints);
        unfocus(this);
    }

    public void unfocus(Component component) {
        if (!(component instanceof JTextField)) {
            component.setFocusable(false);
        }
        if (component instanceof Container) {
            for (Component c : ((Container) component).getComponents()) {
                unfocus(c);
            }
        }
    }
}
