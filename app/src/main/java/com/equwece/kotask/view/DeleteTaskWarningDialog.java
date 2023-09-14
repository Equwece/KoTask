package com.equwece.kotask.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.FetchTaskListWorker;

public class DeleteTaskWarningDialog extends JFrame {

    public DeleteTaskWarningDialog(AppEnv appEnv, TaskItemComponent selectedItem) {
        super();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        int deletionChoce = JOptionPane.showConfirmDialog(
                this,
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
