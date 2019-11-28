package Lab1;

import Lab1.Entities.Task;
import Lab1.Entities.TaskLog;

public class ControllerWriteDelete {
    private volatile TaskLog manager;
    private volatile TaskLog managerOut;

    public ControllerWriteDelete(TaskLog manager, TaskLog managerOut) {
        this.manager = manager;
        this.managerOut = managerOut;
    }

    public void writeDelete(int count) {
        int i = -1;
        while (i < count) {
            managerOut.createTask(manager.getTasks()[count]);
            manager.deleteTask(count);
            i++;
        }

    }
}
