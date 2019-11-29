package taskmanager;

import taskmanager.controllers.Controller;
import taskmanager.entities.Task;
import taskmanager.entities.TaskLog;
import taskmanager.view.UserInterface;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        LinkedList<Task> myTaskLog = new LinkedList<Task>();
        LinkedList<Task> myTaskLog1 = new LinkedList<Task>();
        TaskLog manager = new TaskLog("Мой менеджер задач",myTaskLog);
        TaskLog managerOut = new TaskLog("Мой менеджер выполненных задач",myTaskLog1);
        UserInterface userInterface=new UserInterface(manager,managerOut);
        userInterface.mainMenu();
        System.out.println("Выход1");
    }
}
