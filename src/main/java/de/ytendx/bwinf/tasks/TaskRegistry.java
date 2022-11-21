package de.ytendx.bwinf.tasks;

import de.ytendx.bwinf.tasks.container.ContainerTask;
import de.ytendx.bwinf.tasks.störung.StörungTask;
import de.ytendx.bwinf.tasks.reimerei.ReimereiTask;

import java.util.Optional;
import java.util.stream.Stream;

public class TaskRegistry {

    public static final Task[] tasks = {
            new ReimereiTask(),
            new StörungTask(),
            new ContainerTask()
    };

    public Optional<Task> getTask(String name){
        return Stream.of(tasks)
                .filter(task -> task.getClass().getAnnotation(Id.class).name().equalsIgnoreCase(name))
                .findAny();
    }

}
