package com.equwece.kotask.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.equwece.kotask.AppEnv;

import net.miginfocom.swing.MigLayout;

public class AppWindow extends JFrame {

    final private AppEnv appEnv;

    public AppWindow(AppEnv appEnv) {
        super("KoTask");
        this.appEnv = appEnv;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();

        MigLayout mainLayout = new MigLayout("fillx");

        contentPane.setLayout(mainLayout);

        TaskList taskList = new TaskList(this.appEnv.getTaskDao());

        contentPane.add(taskList, "grow");

        this.getContentPane().add(contentPane);

    }

    public void run() {
        // Get window size from OS or use constant.
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        this.setVisible(true);
    }

}
