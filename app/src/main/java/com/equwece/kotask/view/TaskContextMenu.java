package com.equwece.kotask.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.DeleteTaskAction;
import com.equwece.kotask.controller.OpenTaskEditorAction;
import com.equwece.kotask.controller.ToggleTaskDoneAction;
import com.equwece.kotask.data.TaskItem.TaskStatus;

public class TaskContextMenu extends JPopupMenu {
    private final AppEnv appEnv;
    private final TaskItemComponent selectedItem;

    public TaskContextMenu(AppEnv appEnv, TaskItemComponent selectedItem) {
        super();
        this.appEnv = appEnv;
        this.selectedItem = selectedItem;
        Boolean itemIsDone = selectedItem.getTaskItem().getTaskStatus() == TaskStatus.DONE;

        JMenuItem toggleDone = new JMenuItem(String.format("Mark as %s",
                itemIsDone ? "active" : "done"));
        toggleDone.addActionListener(new ToggleTaskDoneAction(this.appEnv, this.selectedItem));
        this.add(toggleDone);

        JMenuItem editItem = new JMenuItem("Edit task");
        editItem.addActionListener(new OpenTaskEditorAction(this.appEnv, this.selectedItem));
        this.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete task");
        deleteItem.addActionListener(new DeleteTaskAction(this.appEnv, this.selectedItem));
        this.add(deleteItem);

    }

    public AppEnv getAppEnv() {
        return appEnv;
    }

    public TaskItemComponent getSelectedItem() {
        return selectedItem;
    }

}
