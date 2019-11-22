package Lab1;

import Lab1.Entities.Contacts;
import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Controller {

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

    public static void serialisationTaskLog(OutputStream out, LinkedList<Task> linkedListTask, boolean relevant) throws IOException {
        LinkedList<Task> taskLogList = new LinkedList<Task>();
        for (Task task : linkedListTask) {
            if (task.getRelevant() == relevant) {
                taskLogList.add(task);
            }
        }
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(taskLogList);
    }

    public static LinkedList<Task> deserialisationTaskLog(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = new ObjectInputStream(in);
        LinkedList<Task> listTaskLog=(LinkedList<Task>)objectIn.readObject();
        return  listTaskLog;
    }

    public static String parseString(Scanner in, String name) {
        name = in.nextLine();
        while ("".equals(name)) {
            System.out.println("\nВы ничего не ввели.Повторите ввод:");
            name = in.nextLine();
        }

        return name;
    }

    public static int parseInt(Scanner in, String value) {
        boolean exit = true;
        int valueFinal = 0;
        value = in.nextLine();
        while (exit) {
            if ("".equals(value)) {
                System.out.println("\nВы ничего не ввели.Повторите ввод:");
            } else {
                if (!(value.matches("-?\\d+(\\.\\d+)?"))) {
                    System.out.println("\nНекорректный ввод данных.Повторите ввод:");
                } else {
                    break;
                }
            }
            value = in.nextLine();
        }
        valueFinal = Integer.parseInt(value);
        return valueFinal;
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
            int proverca = date.compareTo(Calendar.getInstance().getTime());
            if (((day > 0) & (day < 32)) & ((m > 0) & (m < 12)) & (year > 2018) & ((ch > 0) & (ch < 25)) & ((min > -1) & (min < 61)) & (proverca > -1)) {
                exit = false;
            } else {
                UserInterface.cls();
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
}



