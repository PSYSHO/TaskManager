package taskmanager.alert;

import taskmanager.controllers.Controller;
import taskmanager.controllers.ControllerWriteDelete;
import taskmanager.entities.TaskLog;

import java.io.Serializable;


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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                count = controller.notification(manager);
            }catch(InterruptedException e)
            {
                break;
            }
            if (count>-1) {
                cwd.Delete();
            }
        }
        System.out.println("ВЫХОД");
    }
}


