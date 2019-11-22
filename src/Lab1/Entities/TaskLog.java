package Lab1.Entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

public class TaskLog implements Serializable {
    private String name;
    private LinkedList<Task> tasksList = new LinkedList<Task>();
    private LinkedList<Task> tasksListNoRelevant = new LinkedList<Task>();

    public TaskLog(String name) {
        this.name = name;
    }

    public TaskLog(String name, LinkedList<Task> tasksList) {
        this.name = name;
        this.tasksList = tasksList;
    }

    public TaskLog() {
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public Task[] getTasks() {
        Task[] array = new Task[tasksList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = tasksList.get(i);
        }
        return array;
    }

    public LinkedList<Task> getTasksList() {
        return tasksList;
    }

    //создание
    public void createTask(Task task) {
        tasksList.add(task);
    }

    //удаление
    public void deleteTask(int num) {
        tasksList.remove(num);
    }

    //изменение
    public void setTask(int index, Task task_log) {
        tasksList.set(index, task_log);
    }

    //представление чеез массив
    public Task[] getTaskLog() {
        Task[] array = new Task[tasksList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = tasksList.get(i);
        }
        return array;
    }

    //просмотр
    public String toString() {
        StringBuffer str = new StringBuffer();
        Collections.sort(tasksList);
        int i = 0;
        if (tasksList != null) {
            for (Task element : tasksList) {
                str.append(i + ". Task\n")
                        .append(element.toString() + "\n");
                i++;
            }
        }
        return str.toString();
    }

    //количество элементов
    public int getSize() {
        return tasksList.size();
    }

    public int getSizeRelevant() {
        int i = 0;
        for (Task element : tasksList) {
            if (element.getRelevant()) {
                i++;
            }
        }
        return i;
    }

}
