/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.equwece.kotask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.swing.UIManager;

import org.jdbi.v3.core.Jdbi;

import com.equwece.kotask.controller.TaskController;
import com.equwece.kotask.data.SqliteTaskDao;
import com.equwece.kotask.data.TaskDao;
import com.equwece.kotask.data.TaskItem;
import com.equwece.kotask.data.TaskItem.TaskStatus;
import com.equwece.kotask.view.AppWindow;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class App {
    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(AppEnv appEnv) {
        // Create and set up the window.
        AppWindow appWindow = new AppWindow(appEnv);
        appWindow.run();
    }

    public static Path setupAppDir() throws IOException {
        String userHome = System.getProperty("user.home");
        if (userHome == null) {
            throw new IOException("Can't get user's home directory");
        }
        Path appDirPath = Paths.get(userHome, ".KoTask");
        Files.createDirectories(appDirPath);
        return appDirPath;
    }

    public static void setupDB(Path appDirPath, Jdbi jdbi) {
        if (!appDirPath.resolve("cache.db").toFile().exists()) {
            jdbi.withHandle(handle -> {
                handle.execute(
                        "CREATE TABLE \"task\" ("
                                + "\"head_line\"	TEXT NOT NULL,"
                                + "\"description\"	TEXT,"
                                + "\"id\"	TEXT NOT NULL,"
                                + "\"status\" TEXT NOT NULL DEFAULT 'ACTIVE',"
                                + "\"creation_date\" TEXT NOT NULL,"
                                + "CONSTRAINT \"status_check\" CHECK(status IN ('ACTIVE','DONE')),"
                                + "PRIMARY KEY(\"id\"));");
                return null;
            });

        }
    }

    public static void main(String[] args) throws IOException {

        Path appDirPath = setupAppDir();

        Jdbi jdbi = Jdbi.create(String.format("jdbc:sqlite:%s", appDirPath.resolve("cache.db").toString()));

        jdbi.registerRowMapper(TaskItem.class,
                (rs, ctx) -> {
                    String maybeDescription = rs.getString("description");
                    Optional<String> description;
                    if (maybeDescription == null) {
                        description = Optional.empty();
                    } else {
                        description = Optional.of(maybeDescription);
                    }

                    String taskStatusStr = rs.getString("status");
                    TaskStatus taskStatus;
                    switch (taskStatusStr) {
                        case "DONE":
                            taskStatus = TaskStatus.DONE;
                            break;
                        default:
                            taskStatus = TaskStatus.ACTIVE;
                            break;
                    }

                    String creationDateStr = rs.getString("creation_date");
                    LocalDateTime creationDate = LocalDateTime.parse(creationDateStr);

                    return new TaskItem(rs.getString("head_line"), UUID.fromString(rs.getString("id")),
                            description, taskStatus, creationDate);
                });

        setupDB(appDirPath, jdbi);

        TaskDao taskDao = new SqliteTaskDao(jdbi);
        TaskController taskController = new TaskController(taskDao);
        AppEnv appEnv = new AppEnv(taskDao, taskController);

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        FlatLaf.registerCustomDefaultsSource("themes");
        FlatDarkLaf.setup();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(appEnv);
            }
        });
    }
}
