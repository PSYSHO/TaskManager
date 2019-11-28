package Lab1.Alert;

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
                    Message(task.getName(), task.getDescription(), task.getContactsString());
                    task.setRelevant(false);
                    break;
                } else if ((task.getData().equals(now)) & (task.getRelevant())) {
                    Message(task.getName(), task.getDescription(), task.getContactsString());
                    task.setRelevant(false);
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public static void Message(String title, String description, String[] contact) {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("Image/tray.gif");
            TrayIcon trayIcon = new TrayIcon(image);
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            if (contact.length==0) {
                trayIcon.displayMessage(title, description + "\n" + "Нет контактов", TrayIcon.MessageType.INFO);
            }else{
                StringBuffer str=new StringBuffer();
                //String str="";
                for (String element:contact){
                    str.append(element);
                }
                //contact=contact.substring(1,contact.length()-2);
                trayIcon.displayMessage(title, description + "\n" + "Контакты:\n"+str, TrayIcon.MessageType.INFO);
            }
        }

    }
}
