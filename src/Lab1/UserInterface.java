package Lab1;

import Lab1.AlertClass.Alert;
import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import static Lab1.Utilities.Utilities.*;

public class UserInterface {
    private TaskLog manager;
    private TaskLog managerOut;
    private Controller controller=new Controller();

    public UserInterface(TaskLog manager,TaskLog managerOut){
        this.manager=manager;
        this.managerOut=managerOut;
    }

    public void mainMenu() {
        manager=controller.downlandTaskLog(new File("relevantFile"),manager);
        managerOut=controller.downlandTaskLog(new File("noRelevantFile"),managerOut);
        Runnable run1 = new Alert(controller,manager,managerOut);
        Thread thread1 = new Thread(run1);
        thread1.setDaemon(true);
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
                    try {
                        createMenu(manager);
                    }catch(InterruptedException e){System.out.println("Ошибка типа InterruptedException");}
                    break;
                case "2":
                    cls();
                    try {
                        deleteMenu(manager);
                    }catch(InterruptedException e){System.out.println("Ошибка типа InterruptedException");}
                    break;
                case "3":
                    cls();
                    try{
                        setMenu(manager);
                    }catch(InterruptedException e){System.out.println("Ошибка типа InterruptedException");}
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
                    exit = true;
                    thread1.interrupt();
                    try {
                        Method method = Alert.class.getMethod("setExit", boolean.class);
                        method.invoke(run1, false);
                    }
                    catch (NoSuchMethodException e) {
                        System.out.println(e.getMessage());}
                    catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    try (FileOutputStream fileOutputStream = new FileOutputStream("relevantFile")) {
                        controller.serialisationTaskLog(fileOutputStream, manager, true);
                    } catch (IOException e) { }
                    finally {
                        exit = true;
                    }

                    try (FileOutputStream fileOutputStream = new FileOutputStream("noRelevantFile")) {
                        controller.serialisationTaskLog(fileOutputStream,managerOut,  false);
                    } catch (IOException e) { }
                    finally {

                        thread1.interrupt();
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

    private void createMenu(TaskLog manager)throws InterruptedException {
        Scanner in = new Scanner(System.in);
        boolean exitCreateMenu = false;
        System.out.println(
                "1.Начать создание новой задачи;\n" +
                        "2.Назад;\n");
        String str = in.nextLine();
        switch (str) {
            case "1":
                cls();
                controller.setFlag(false);
                manager.createTask(controller.inputTask(in));
                controller.setFlag(true);
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

    private void deleteMenu(TaskLog manager) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        String value = "";
        boolean exitCreateMenu = false;
        if(manager.getSize()>0) {
            while (!exitCreateMenu) {
                System.out.println(manager);
                System.out.println("Введите индекс элемента который нужно удалить ");
                int num = parseInt(in, value);
                if ((num > -1) && (num < manager.getSize())) {
                    cls();
                    System.out.println("Вы точно хотите удалить " + num + " задачу?\n" +
                            "Если - да, нажмите 1\n" +
                            "Если нет - то, нажмите 2");
                    String num1 = parseString(in, value);
                    switch (num1) {
                        case "1":
                            controller.setFlag(false);
                            controller.deleteTask(num,manager);
                            controller.setFlag(true);
                            cls();
                            System.out.println("Элемент успешно удален\n " +
                                    "------------------------------------------");
                            exitCreateMenu = true;
                            break;
                        case "2":
                            cls();
                            exitCreateMenu = true;
                            break;
                        default:
                            cls();
                            System.out.println("Неверно введенный символ\n" +
                                    "--------------------------------------------");
                            break;
                    }
                } else {
                    cls();
                    System.out.println("Неверно введенный индекс\n" +
                            "----------------------------------------------");
                }
            }
        }else{
           System.out.println("Журнал задач пуст\n" +
                   "----------------------------------------------");
        }
    }

    private void setMenu(TaskLog manager)throws InterruptedException {
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
                    int num = parseInt(in, value);
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
                    int num = parseInt(in, value);
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
                                int size = parseInt(in, value);
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
}

