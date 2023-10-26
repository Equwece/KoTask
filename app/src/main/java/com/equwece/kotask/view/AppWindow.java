package com.equwece.kotask.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskItem;

import net.miginfocom.swing.MigLayout;

public class AppWindow extends JFrame {

    private TaskList taskList;

    public AppWindow(AppEnv appEnv) {
        super("KoTask");
        appEnv.setAppWindow(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TaskController taskController = appEnv.getTaskController();

        JPanel contentPane = new JPanel();

        MigLayout mainLayout = new MigLayout("fillx", "0[]0", "[][]");

        contentPane.setLayout(mainLayout);

        TaskList taskList = new TaskList(appEnv);
        this.taskList = taskList;

        new SwingWorker<List<TaskItem>, Void>() {

            @Override
            protected List<TaskItem> doInBackground() throws Exception {
                return taskController.getAllItems();
            }

            @Override
            protected void done() {
                try {
                    taskList.updateTaskList(get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.execute();

        ToolBar toolBar = new ToolBar(appEnv);

        contentPane.add(toolBar, "wrap, gapx 0 0");
        contentPane.add(taskList, "grow, gapx 0 0");

        this.getContentPane().add(contentPane);
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void run() {
        // Get window size from OS or use constant.
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

}
