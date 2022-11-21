package de.ytendx.bwinf.tasks.container;

import de.ytendx.bwinf.io.impl.ContainerInput;
import de.ytendx.bwinf.tasks.Id;
import de.ytendx.bwinf.tasks.Task;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Id(name = "container")
public class ContainerTask implements Task {
    @Override
    public void execute() {
        ContainerInput input = new ContainerInput();

        System.out.println("Bitte gib die container file an:");
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String fileName = null;

        try {
            fileName = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Preconditions.require(fileName != null);

        input.read(fileName);

        // Doing it like you should ;) XDP style <3

        var buffer = new int[getMaxContainer(input)];
        var cursor = 0;

        for(var pair : input.getContainerInput()){
            var heavyPos = getPosition(pair.getA(), buffer);
            var lightPos = getPosition(pair.getB(), buffer);

            handle: {
                if (heavyPos == -1 && lightPos == -1) {
                    move(cursor, buffer.length-1, buffer);
                    move(cursor, buffer.length-1, buffer);
                    buffer[cursor] = pair.getB();
                    cursor++;
                    buffer[cursor] = pair.getA();
                    break handle;
                }

                // Only one is possible because of the above statement
                if (heavyPos == -1) {
                    cursor = lightPos + 1;
                    move(cursor, buffer.length-1, buffer);
                    buffer[cursor] = pair.getA();
                    break handle;
                }

                if (lightPos == -1) {
                    move(0, buffer.length-1, buffer);
                    buffer[cursor] = pair.getB();
                    break handle;
                }

                // This gets executed when both are already in the buffer
                if (lightPos < heavyPos){
                    // Just let it go cause its already in the right order
                    break handle;
                }

                moveBack(heavyPos+1, lightPos, buffer);
                cursor = lightPos;
                buffer[cursor] = pair.getA();
            }

            // Reset cursor
            cursor = 0;
        }

        System.out.println(Arrays.toString(buffer));
    }

    public int getPosition(int val, int[] data){
        for (int cursor = 0; cursor < data.length; cursor++){
            if (data[cursor] == val){
                return cursor;
            }
        }

        return -1;
    }

    public void move(int from, int till, int[] data){

        int[] readData = data.clone();

        for(int i = from; i < till; i++){
            var v = readData[i];
            data[i+1] = v;
        }
    }

    public void moveBack(int from, int till, int[] data){
        int[] readData = data.clone();
        for(int i = from; i <= till; i++){
            data[i-1] = readData[i];
        }
    }

    public int getMax(Integer[] array){
        int curr = 0;
        for(int i : array){
            if (curr < i) {
                curr = i;
            }
        }
        return curr;
    }

    public List<Integer> heavierThenTheOthers(int container, ContainerInput input){
        List<Integer> list = new CopyOnWriteArrayList<>();
        input.getContainerInput().forEach(pair -> {
            if (pair.getA() == container){
                list.add(pair.getB());
            }
        });
        return list;
    }

    public int getMaxContainer(ContainerInput input){
        AtomicInteger biggest = new AtomicInteger();
        input.getContainerInput().forEach(pair -> {
            if (pair.getA() > biggest.get()) {
                biggest.set(pair.getA());
            }

            if (pair.getB() > biggest.get()){
                biggest.set(pair.getB());
            }
        });
        return biggest.get();
    }
}
