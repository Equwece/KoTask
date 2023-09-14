package com.equwece.kotask.view;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.FetchTaskListWorker;

public class DeleteTaskWarningDialog {

    public DeleteTaskWarningDialog(AppEnv appEnv, TaskItemComponent selectedItem) {
        int deletionChoce = JOptionPane.showConfirmDialog(
                appEnv.getAppWindow(),
                "This task will be completely deleted, are you sure?",
                "Are you sure?",
                JOptionPane.WARNING_MESSAGE);
        if (deletionChoce == 0) {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    appEnv.getTaskController().deleteItem(selectedItem.getTaskItem().getId());
                    return null;
                }

                @Override
                protected void done() {
                    new FetchTaskListWorker(appEnv.getTaskController()) {
                        @Override
                        protected void done() {
                            try {
                                appEnv.getAppWindow().getTaskList().updateTaskList(get());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.execute();
                }

            }.execute();
        }

    }

}
