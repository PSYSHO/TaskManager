package AlertClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            for(Task task:journal.getJournal()){
                if(task.getDate().before(now)){
                    Message(task.title,task.description,task.contacts);
                }
                else if(task.getDate().equals(now)){
                    Message(task.title,task.description,task.contacts);
                }
            }
            try {
                Thread.sleep(60000);
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
        Journal journal = new Journal();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = null,test=null,test1 = null;
        try {
            date = f.parse("2019-11-19 20:02");
            test1 = f.parse("2019-11-21 12:50");
            test= f.parse("2019-11-21 16:25");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    Task task2 = new Task("Звонок","Звонок домой",test,"Лена-8924214752");
    Task task = new Task("Диалог","Звонок на работу",date,"Вика-8924214752");
    Task task3 = new Task("Звонок","Ремонт машины",test1,"Петрович-8924214752");
    journal.add(task);
    journal.add(task2);
    journal.add(task3);
    Thread thread = new Alert(journal);
    thread.start();
    //thread.interrupt();
    //Message(task.title,task.description,task.contacts);
    }
}
