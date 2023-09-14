package com.equwece.kotask;

import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TagDao;
import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.view.AppWindow;

final public class AppEnv {
    final private TaskDao taskDao;
    final private TagDao tagDao;
    final private TaskController taskController;

    private AppWindow appWindow;
    public AppEnv(TaskDao taskDao, TagDao tagDao, TaskController taskController) {
        this.taskDao = taskDao;
        this.tagDao = tagDao;
        this.taskController = taskController;
    }

    public TagDao getTagDao() {
        return tagDao;
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
