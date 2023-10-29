package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;
import java.util.UUID;

import javax.swing.AbstractAction;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.view.TaskCreatorPanel;

final public class OpenTaskCreatorAction extends AbstractAction {

    final private AppEnv appEnv;
    final private UUID ascendantTaskId;

    public OpenTaskCreatorAction(AppEnv appEnv, UUID ascendantTaskId) {
        this.appEnv = appEnv;
        this.ascendantTaskId = ascendantTaskId;
    }

    public OpenTaskCreatorAction(AppEnv appEnv) {
        this.appEnv = appEnv;
        this.ascendantTaskId = TaskController.ROOT_TASK_ID;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new TaskCreatorPanel("Create task", this.appEnv, this.ascendantTaskId).setupPanelWidgets().run();
    }

}
