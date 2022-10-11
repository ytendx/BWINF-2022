package de.ytendx.bwinf.tasks;

import de.ytendx.bwinf.tasks.Störung.StörungTask;
import de.ytendx.bwinf.tasks.reimerei.ReimereiTask;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.stream.Stream;

public class TaskRegistry {

    public static final Task[] tasks = {
            new ReimereiTask(),
            new StörungTask()
    };

    public Optional<Task> getTask(String name){
        return Stream.of(tasks)
                .filter(task -> task.getClass().getAnnotation(Id.class).name().equalsIgnoreCase(name))
                .findAny();
    }

}
