package com.equwece.kotask.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import com.equwece.kotask.AppEnv;
import com.equwece.kotask.controller.DeleteTaskAction;
import com.equwece.kotask.controller.OpenTaskCreatorAction;
import com.equwece.kotask.controller.OpenTaskEditorAction;
import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.TaskItem;

public class TaskList extends JList<TaskItemComponent> {
    final private TaskController taskController;
    final private AppEnv appEnv;

    public TaskList(AppEnv appEnv) {
        super();
        this.appEnv = appEnv;
        this.taskController = appEnv.getTaskController();
        this.setCellRenderer(new TaskListComponentRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.setLayoutOrientation(JList.VERTICAL);
        this.setVisibleRowCount(-1);

        this.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent event) {
                        switch (event.getKeyChar()) {
                            case 'j': {
                                int currentListInd = TaskList.this.getSelectedIndex();
                                TaskList.this.setSelectedIndex(currentListInd + 1);
                                break;
                            }
                            case 'k': {
                                int currentListInd = TaskList.this.getSelectedIndex();
                                TaskList.this.setSelectedIndex(currentListInd - 1);
                                break;
                            }

                            case 'e': {
                                TaskItemComponent selectedItem = TaskList.this.getModel()
                                        .getElementAt(TaskList.this.getSelectedIndex());
                                // TODO: Bad pattern, pass null to action listener's method
                                new OpenTaskEditorAction(TaskList.this.appEnv, selectedItem).actionPerformed(null);
                                break;
                            }

                            case 'd': {
                                TaskItemComponent selectedItem = TaskList.this.getModel()
                                        .getElementAt(TaskList.this.getSelectedIndex());
                                // TODO: Bad pattern, pass null to action listener's method
                                new DeleteTaskAction(TaskList.this.appEnv, selectedItem).actionPerformed(null);
                                break;
                            }

                            case KeyEvent.VK_DELETE: {
                                TaskItemComponent selectedItem = TaskList.this.getModel()
                                        .getElementAt(TaskList.this.getSelectedIndex());
                                // TODO: Bad pattern, pass null to action listener's method
                                new DeleteTaskAction(TaskList.this.appEnv, selectedItem).actionPerformed(null);
                                break;
                            }
                        }
                    }
                });

        this.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopup(e);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopup(e);
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            TaskItemComponent selectedItem = this.getSelectedComponent(e);
                            new TaskEditorPanel("Edit task", appEnv, selectedItem.getTaskItem())
                                    .setupPanelWidgets()
                                    .run();
                        }
                    }

                    public TaskItemComponent getSelectedComponent(MouseEvent e) {
                        TaskItemComponent selectedComponent = TaskList.this.getModel()
                                .getElementAt(TaskList.this.locationToIndex(e.getPoint()));
                        return selectedComponent;
                    }

                    public void ensureItemVisuallySelected(MouseEvent e) {
                        int itemIndex = TaskList.this.locationToIndex(e.getPoint());
                        TaskList.this.setSelectedIndex(itemIndex);
                    }

                    private void showPopup(MouseEvent e) {
                        this.ensureItemVisuallySelected(e);
                        TaskContextMenu popup = new TaskContextMenu(appEnv, this.getSelectedComponent(e));
                        popup.show(e.getComponent(),
                                e.getX(), e.getY());
                    }
                });

        this.getInputMap().put(KeyStroke.getKeyStroke('n'), "createNewTask");
        this.getActionMap().put("createNewTask", new OpenTaskCreatorAction(appEnv));

        this.setSelectedIndex(0);
    }

    public void updateTaskList(List<TaskItem> newTaskList) {
        sortTaskListByDate(newTaskList);
        TaskItemComponent[] newTaskComponents = this.constructTaskComponents(newTaskList);
        this.setListData(newTaskComponents);
    }

    public void sortTaskListByDate(List<TaskItem> taskList) {
        taskList.sort(Comparator.comparing(TaskItem::getCreationDate));
        Collections.reverse(taskList);
    }

    public TaskItemComponent[] constructTaskComponents(List<TaskItem> items) {
        TaskItemComponent[] taskItems = new TaskItemComponent[items.size()];
        for (int i = 0; i < items.size(); i++) {
            taskItems[i] = new TaskItemComponent(items.get(i));
        }
        return taskItems;
    }
}
