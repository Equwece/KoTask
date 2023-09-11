package com.equwece.kotask;

import com.equwece.kotask.data.TaskDao;

final public class AppEnv {
    final private TaskDao taskDao;

    public AppEnv(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }
}
