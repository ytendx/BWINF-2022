package de.ytendx.bwinf;

import de.ytendx.bwinf.io.impl.ContainerInput;
import de.ytendx.bwinf.io.impl.PairInput;
import de.ytendx.bwinf.io.impl.TextInput;
import de.ytendx.bwinf.tasks.Task;
import de.ytendx.bwinf.tasks.TaskRegistry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class Launcher {

    public static void main(String[] args) throws IOException {
        System.out.println("Gib den zu startenden Task an:");

        TaskRegistry taskRegistry = new TaskRegistry();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Optional<Task> reimereiTask = taskRegistry.getTask(bufferedReader.readLine());

        reimereiTask.get().execute();
    }


}
