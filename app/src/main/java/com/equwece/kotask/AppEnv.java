package com.equwece.kotask;

import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.view.AppWindow;

final public class AppEnv {
    final private TaskDao taskDao;
    final private TaskController taskController;
    private AppWindow appWindow;

    public AppEnv(TaskDao taskDao, TaskController taskController) {
        this.taskDao = taskDao;
        this.taskController = taskController;
    }

    public TaskController getTaskController() {
        return taskController;
    }

    public AppWindow getAppWindow() {
        return appWindow;
    }

    public void setAppWindow(AppWindow appWindow) {
        this.appWindow = appWindow;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }
}
