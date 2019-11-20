package AlertClass;

import java.util.Date;
import java.awt.*;


public class Alert extends Thread {
    public Journal journal;
    public Alert(Journal journal){
        this.journal = journal;
    }
    @Override
    public void run() {
        while(true){
            Date now = new Date();
            Date soon  = new Date(now.getTime());
            for(Task task:journal.getJournal()){
                if(task.getDate().before(now)){
                    Message(task.title,task.description,task.contacts);
                    journal.delete(task);
                }
                else if(task.getDate().after(now))Message(task.title,task.description,task.contacts);
            }
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


        public static void Message(String title, String description, String contact){
            if(SystemTray.isSupported()) {
                SystemTray systemTray = SystemTray.getSystemTray();
                java.awt.Image image = Toolkit.getDefaultToolkit().getImage("Image/tray.gif");
                TrayIcon trayIcon = new TrayIcon(image);
                try {
                    systemTray.add(trayIcon);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                trayIcon.displayMessage(title, description +"\n"+contact, TrayIcon.MessageType.INFO);
        }

    }
    public static void main(String[] args){
    Date date = new Date(2019,11,19,20,02);
    Date test = new Date(2019,11,19,20,05);
    Task task2 = new Task("Звонок","Звонок домой",test,"Лена-8924214752");
    Task task = new Task("Звонок","Звонок домой",date,"Вика-8924214752");
    Journal journal = new Journal();
    journal.add(task);
    journal.add(task2);
    Thread thread = new Alert(journal);
    thread.start();
    //thread.interrupt();
    //Message(task.title,task.description,task.contacts);
    }
}
