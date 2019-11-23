package Lab1;

import Lab1.AlertClass.Alert;
import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class UserInterface {
    public void mainMenu() {
        LinkedList<Task> myTaskLog = new LinkedList<Task>();
        LinkedList<Task> myTaskLog1 = new LinkedList<Task>();
        myTaskLog = downlandTaskLog(myTaskLog);
        TaskLog manager = new TaskLog("Мой менеджер задач", myTaskLog);
        myTaskLog1 = downlandTaskLogOut(myTaskLog1);
        TaskLog managerOut = new TaskLog("Мой менеджер выполненных задач", myTaskLog1);
        Runnable run1 = new Alert(manager);
        Thread thread1 = new Thread(run1);
        thread1.start();
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\t\tГлавное меню:\n" +
                    "-------------------------------------------------\n" +
                    "1.Создать новую задачу;\n" +
                    "2.Удалить задачу;\n" +
                    "3.Изменить задачу;\n" +
                    "4.Просмотр всех задач;\n" +
                    "5.Просмотр выполненых задач;\n" +
                    "6.Очистить всю историю задач;\n" +
                    "7.Выход.\n");

            switch (in.nextLine()) {
                case "1":
                    cls();
                    createMenu(manager);
                    break;
                case "2":
                    cls();
                    deleteMenu(manager);
                    break;
                case "3":
                    cls();
                    setMenu(manager);
                    exitMainMenu(in);
                    break;
                case "4":
                    cls();
                    System.out.println(manager);
                    exitMainMenu(in);
                    break;
                case "5":
                    cls();
                    System.out.println(managerOut);
                    exitMainMenu(in);
                    break;
                case "6":
                    cls();
                    managerOut.getTasksList().clear();
                    System.out.println("Все задачи удалены\n" +
                            "--------------------------------------");
                    exitMainMenu(in);
                    break;
                case "7":
                    try (FileOutputStream fileOutputStream = new FileOutputStream("serialisation1")) {
                        Controller.serialisationTaskLog(fileOutputStream, manager.getTasksList(), true);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                    try (FileOutputStream fileOutputStream = new FileOutputStream("serialisation")) {
                        for (Task task : manager.getTasksList()) {
                            if (!task.getRelevant()) {
                                managerOut.createTask(task);
                            }
                        }
                        Controller.serialisationTaskLog(fileOutputStream, managerOut.getTasksList(), false);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                    exit = true;
                    thread1.interrupt();
                    try {
                        Method method = Alert.class.getMethod("setExit", boolean.class);
                        method.invoke(run1, false);
                    }
                    catch (NoSuchMethodException e) {
                        System.out.println(e.getMessage());}
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    cls();
                    System.out.println("Неверно введенный символ\n" +
                            "-----------------------------------------");
                    break;
            }
        }
    }

    private void createMenu(TaskLog manager) {
        Scanner in = new Scanner(System.in);
        boolean exitCreateMenu = false;
        System.out.println(
                "1.Начать создание новой задачи;\n" +
                        "2.Назад;\n");
        String str = in.nextLine();
        switch (str) {
            case "1":
                cls();
                manager.createTask(Controller.inputTask(in));
                cls();
                System.out.println("Элемент успешно добавлен\n " +
                        "------------------------------------------");
                break;
            case "2":
                cls();
                break;
            default:
                cls();
                System.out.println("Неверно введенный символ\n" +
                        "----------------------------------------");
                break;
        }
    }

    private void deleteMenu(TaskLog manager) {
        Scanner in = new Scanner(System.in);
        String value = "";
        boolean exitCreateMenu = false;
        System.out.println(
                "1.Удалить задачу;\n" +
                        "2.Назад;\n");
        String str = in.nextLine();
        switch (str) {
            case "1":
                cls();
                while (!exitCreateMenu) {
                    System.out.println(manager);
                    System.out.println("Введите индекс элемента который нужно удалить ");
                    int num = Controller.parseInt(in, value);
                    if ((num > -1) && (num < manager.getSize())) {
                        manager.deleteTask(num);
                        cls();
                        System.out.println("Элемент успешно удален\n " +
                                "------------------------------------------");
                        exitCreateMenu = true;
                    } else {
                        cls();
                        System.out.println("Неверно введенный индекс" +
                                "----------------------------------------------");
                    }
                }
                break;
            case "2":
                break;
            default:
                cls();
                System.out.println("Неверно введенный символ\n" +
                        "--------------------------------------------");
                break;
        }
    }

    private void setMenu(TaskLog manager) {
        Scanner in = new Scanner(System.in);
        String value = "";
        boolean exitCreateMenu = false;
        System.out.println(
                "1.Изменить всю задачу;\n" +
                        "2.Изменить отдельные компоненты программы\n" +
                        "3.Назад;\n");
        String str = in.nextLine();
        switch (str) {
            case "1":
                cls();
                while (!exitCreateMenu) {
                    System.out.println(manager);
                    System.out.println("Введите индекс задачи, которую хотите изменить:");
                    int num = Controller.parseInt(in, value);
                    if ((num > -1) && (num < manager.getSize())) {
                        System.out.println("\nВведите все данные заного для задачи которую хотите изменить:");
                        manager.setTask(num, Controller.inputTask(in));
                        cls();
                        System.out.println("Элемент успешно изменен \n" +
                                "------------------------------------------");
                        exitCreateMenu = true;
                    } else {
                        cls();
                        System.out.println("Неверно введенный индекс" +
                                "----------------------------------------------");
                    }
                }
                break;
            case "2":
                cls();
                while (!exitCreateMenu) {
                    System.out.println(manager);
                    System.out.println("Введите индекс задачи, которую хотите изменить(отсчет c 0):");
                    int num = Controller.parseInt(in, value);
                    if ((num > -1) && (num < manager.getSize())) {
                        cls();
                        System.out.println(
                                "1.Изменить имя задачи;\n" +
                                        "2.Изменить описание задачи\n" +
                                        "3.Изменить дату задачи\n" +
                                        "4.Изменить контакты\n" +
                                        "5.Назад;\n ");
                        String str1 = in.nextLine();
                        switch (str1) {
                            case "1":
                                cls();
                                System.out.println("Введите название имени:");
                                manager.getTaskLog()[num].setName(in.nextLine());
                                break;
                            case "2":
                                cls();
                                System.out.println("Введите описание:");
                                manager.getTaskLog()[num].setDescription(in.nextLine());
                                break;
                            case "3":
                                cls();
                                System.out.println("Введите новую дату");
                                manager.getTaskLog()[num].setData(Controller.inputDate(in));
                                break;
                            case "4":
                                cls();
                                System.out.println("Введите количество новых контактов:");
                                int size = Controller.parseInt(in, value);
                                manager.getTaskLog()[num].setContacts(Controller.inputContacts(in, value, size));
                                break;
                            default:
                                cls();
                                System.out.println("Неверно введенный символ\n" +
                                        "--------------------------------------------");
                                break;
                        }
                        cls();
                        System.out.println("Элемент успешно изменен \n" +
                                "------------------------------------------");
                        exitCreateMenu = true;
                    } else {
                        cls();
                        System.out.println("Неверно введенный индекс" +
                                "----------------------------------------------");
                    }
                }
            case "3":
                break;
            default:
                cls();
                System.out.println("Неверно введенный символ\n" +
                        "--------------------------------------------");
                break;
        }
    }

    private void exitMainMenu(Scanner in) {
        System.out.println("\n" +
                "Чтобы выйти в главное меню нажмите 1");
        String q = in.nextLine();
        switch (q) {
            case "1":
                cls();
                break;
            default:
                cls();
                System.out.println("Неверно введенный символ\n" +
                        "-------------------------------------------");
                break;
        }
    }

    private LinkedList<Task> downlandTaskLog(LinkedList<Task> myTaskLog) {
        try (FileInputStream fileInputStream = new FileInputStream("serialisation1")) {
            myTaskLog = Controller.deserialisationTaskLog(fileInputStream);
            System.out.println("\nЗадачи успешно сохранились");
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
        }
        return myTaskLog;
    }

    private LinkedList<Task> downlandTaskLogOut(LinkedList<Task> myTaskLog) {
        try (FileInputStream fileInputStream = new FileInputStream("serialisation")) {
            myTaskLog = Controller.deserialisationTaskLog(fileInputStream);
            System.out.println("\nЗадачи успешно сохранились");
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
        }
        return myTaskLog;
    }

    public static void cls() {
        int ln = 0;
        while (ln < 50) {
            System.out.println();
            ln++;
        }
    }
}

