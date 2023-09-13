package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.view.DeleteTaskWarningDialog;
import com.equwece.kotask.view.TaskItemComponent;

final public class DeleteTaskAction extends AbstractAction {

    final private AppEnv appEnv;
    final private TaskItemComponent selectedItem;

    public DeleteTaskAction(AppEnv appEnv, TaskItemComponent selectedItem) {
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new DeleteTaskWarningDialog(this.appEnv, this.selectedItem);
    }

}
