package com.equwece.kotask;

import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.view.AppWindow;

final public class AppEnv {
    final private TaskDao taskDao;
    private AppWindow appWindow;

    public AppEnv(TaskDao taskDao) {
        this.taskDao = taskDao;
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
