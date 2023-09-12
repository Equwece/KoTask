package com.equwece.kotask.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TaskContextMenu extends JPopupMenu {
    public TaskContextMenu() {
        super();

        JMenuItem deleteItem = new JMenuItem("Delete task");
        this.add(deleteItem);

    }

}
