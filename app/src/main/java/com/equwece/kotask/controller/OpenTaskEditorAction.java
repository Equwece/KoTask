package com.equwece.kotask.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.view.TaskEditorPanel;
import com.equwece.kotask.view.TaskItemComponent;

final public class OpenTaskEditorAction extends AbstractAction {

    final private AppEnv appEnv;
    final private TaskItemComponent selectedItem;

    public OpenTaskEditorAction(AppEnv appEnv, TaskItemComponent selectedItem) {
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new TaskEditorPanel("Edit task", appEnv, selectedItem.getTaskItem())
                .setupPanelWidgets().run();
    }

}
