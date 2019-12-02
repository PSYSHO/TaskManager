package resources.controllers;

import resources.entities.TaskLog;

public class ControllerWriteDelete {
    private volatile TaskLog manager;
    private volatile TaskLog managerOut;

    public ControllerWriteDelete(TaskLog manager, TaskLog managerOut) {
        this.manager = manager;
        this.managerOut = managerOut;
    }

    /*public void writeDelete(int count) {
        int i = 0;
        while((manager.getSize()!=0)&(!manager.getTasks()[0].getRelevant())){
            managerOut.createTask(manager.getTasks()[0]);
            manager.deleteTask(0);
        }
    }*/
    public void Delete(){
        while((manager.getTasks().length!=0)&&(!manager.getTasks()[0].getRelevant())){
            managerOut.createTask(manager.getTasks()[0]);
            manager.deleteTask(0);
        }
    }
}
