package com.equwece.kotask.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.data.TestTaskDao;

import net.miginfocom.swing.MigLayout;

public class AppWindow extends JFrame {
    public AppWindow() {
        super("KoTask");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();

        MigLayout mainLayout = new MigLayout("fillx");

        contentPane.setLayout(mainLayout);

        TaskDao taskDao = new TestTaskDao();
        TaskList taskList = new TaskList(taskDao);

        contentPane.add(taskList, "grow");

        this.getContentPane().add(contentPane);

    }

    public void run() {
        // Get window size from OS or use constant.
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        this.setVisible(true);
    }

}
