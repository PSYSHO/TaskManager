package Lab1.AlertClass;

import Lab1.Controller;
import Lab1.ControllerWriteDelete;
import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;


public class Alert implements Runnable, Serializable {
    private TaskLog manager;
    private ControllerWriteDelete cwd;
    private Controller controller;
    private boolean exit;

    public Alert(Controller controller,TaskLog manager, TaskLog managerOut) {
        this.manager=manager;
        cwd=new ControllerWriteDelete(manager,managerOut);
        this.controller = controller;
        exit = true;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        int count = -1;
        while (exit) {
            try {
                count = controller.notification(manager);
            }catch(InterruptedException e){System.out.println("Ошибка типа InterruptedException");}
            if (count>-1) {
                cwd.writeDelete(count);
            }
        }
    }
}


