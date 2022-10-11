package de.ytendx.bwinf.tasks;

import de.ytendx.bwinf.io.TaskInput;

public interface Task<T extends TaskInput> {

    void execute(T taskInput);

}
