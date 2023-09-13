package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.view.TaskCreatorPanel;

final public class OpenTaskCreatorAction extends AbstractAction {

    final private AppEnv appEnv;

    public OpenTaskCreatorAction(AppEnv appEnv) {
        this.appEnv = appEnv;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new TaskCreatorPanel("Create task", this.appEnv).setupPanelWidgets().run();
    }

}
