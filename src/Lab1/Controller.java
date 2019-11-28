package Lab1;

import Lab1.Entities.Contacts;
import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;
import com.google.gson.Gson;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import static Lab1.Utilities.Utilities.*;

public class Controller {
    private volatile boolean flag;

    public Controller (){
        this.flag=true;
    }

    public void setFlag(boolean flag){
        this.flag=flag;
    }

    public static Task inputTask(Scanner in) throws InputMismatchException, IllegalAccessError {
        boolean exit = true;
        Date date = null;
        String value = "";
        String name = "";
        String description = "";
        System.out.println("Введите название задачи:");
        name = parseString(in, name);
        System.out.println("Введите название описание задачи:");
        description = parseString(in, description);
        date = inputDate(in);
        System.out.print("Какое количество контактов вам понадобится: ");
        int size = parseInt(in, value);
        LinkedList<Contacts> contacts = inputContacts(in, value, size);
        return new Task(name, description, date, contacts);
    }

    public  void deleteTask(int num,TaskLog manager){
        flag=false;
        manager.deleteTask(num);
        flag=true;
    }

    public  void serialisationTaskLog(OutputStream out, TaskLog taskLog, boolean relevant) throws IOException {
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(taskLog);
    }

    public  TaskLog deserialisationTaskLog(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = new ObjectInputStream(in);
        return (TaskLog) objectIn.readObject();
    }

    public static Date inputDate(Scanner in) {
        boolean exit = true;
        Date date = null;
        String value = "";
        int day = -1;
        int year = -1;
        int m = -1;
        int ch = -1;
        int min = -1;
        while (exit) {
            System.out.println("Введите дату и время выполнения задачи");
            System.out.print("Введите год: ");
            year = parseInt(in, value);
            System.out.print("Введите месяц(числовой вариант): ");
            m = parseInt(in, value) - 1;
            System.out.print("Введите день: ");
            day = parseInt(in, value);
            System.out.print("Введите час: ");
            ch = parseInt(in, value);
            System.out.print("Введите минуты: ");
            min = parseInt(in, value);
            GregorianCalendar calendar = new GregorianCalendar(year, m, day, ch, min);
            date = calendar.getTime();
            int compareData = date.compareTo(Calendar.getInstance().getTime());
            if (((day > 0) & (day < 32)) & ((m > 0) & (m < 12)) & (year > 2018) & ((ch > -1) & (ch < 25)) & ((min > -1) & (min < 61)) & (compareData > -1)) {
                exit = false;
            } else
                if(!(compareData > -1)) {
                    cls();
                    System.out.println("Вы ввели не актуальные данные\n" +
                            "Введите данные заного");
                }else
                    {
                        cls();
                        System.out.println("Неправильный формат даты\n" +
                                "Введите данные заного");
                    }
        }
        return date;
    }

    public static LinkedList<Contacts> inputContacts(Scanner in, String value, int size) {
        LinkedList<Contacts> contacts = new LinkedList<Contacts>();
        if (size > 0) {
            for (int i = 0; size > i; i++) {
                System.out.println("Введите имя контакта: ");
                String nameCon = parseString(in, value);
                System.out.println("Введите номер: ");
                String phone = parseString(in, value);
                Contacts con = new Contacts(nameCon, phone);
                contacts.add(con);
            }
        }
        return contacts;
    }
    public synchronized TaskLog downlandTaskLog(File fileName,TaskLog taskLog) {
        flag=false;
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            taskLog = deserialisationTaskLog(fileInputStream);
            System.out.println("\nЗадачи успешно сохранились");
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
        }
        flag=true;
        return taskLog;
    }

    public synchronized int  notification(TaskLog manager) throws InterruptedException{
        while (!flag) {
            //    wait();
        }
        int count=-1;
        Date now = new Date();
        for (Task task : manager.getTasksList()) {
            if ((task.getData().before(now)) & (task.getRelevant())) {
                message(task.getName(), task.getDescription(), task.getContactsString());

                task.setRelevant(false);
                count++;
                break;
                //удалить из менеджера и отправить в таскаут
            } else if ((task.getData().equals(now)) & (task.getRelevant())) {
                message(task.getName(), task.getDescription(), task.getContactsString());
                task.setRelevant(false);

                count++;
                break;
                //удалить из менеджера и отправить в таскаут
            }
        }
        return count;
    }

    public  void message(String title, String description, String[] contact) {

        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
           java.awt.Image image = Toolkit.getDefaultToolkit().getImage("logout.ico");
           TrayIcon trayIcon = new TrayIcon(image);
           try {
               systemTray.add(trayIcon);
           } catch (AWTException e) {
               e.printStackTrace();
           }
            String contacts="";
            for(String element:contact){
                contacts=contacts+element;
            }
            trayIcon.displayMessage(title, description + "\n" + contacts, TrayIcon.MessageType.INFO);
            systemTray.remove(trayIcon);
        }
    }
}



