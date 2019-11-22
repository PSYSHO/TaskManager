package Lab1.AlertClass;

import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;


public class Alert implements Runnable, Serializable {
    public TaskLog taskLog;
    private boolean exit;

    public Alert(TaskLog taskLog) {
        this.taskLog = taskLog;
        exit = true;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        while (exit) {
            Date now = new Date();
            for (Task task : taskLog.getTasksList()) {
                if ((task.getData().before(now)) & (task.getRelevant())) {
                    Message(task.getName(), task.getDescription(), task.getContacts().toString());
                    task.setRelevant(false);
                } else if ((task.getData().equals(now)) & (task.getRelevant())) {
                    Message(task.getName(), task.getDescription(), task.getContacts().toString());
                    task.setRelevant(false);
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void Message(String title, String description, String contact) {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("Image/tray.gif");
            TrayIcon trayIcon = new TrayIcon(image);
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            trayIcon.displayMessage(title, description + "\n" + contact, TrayIcon.MessageType.INFO);
        }

    }
}
