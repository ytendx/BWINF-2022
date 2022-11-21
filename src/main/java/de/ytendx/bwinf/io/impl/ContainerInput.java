package de.ytendx.bwinf.io.impl;

import de.ytendx.bwinf.io.TaskInput;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ContainerInput implements TaskInput {

    private List<Pair<Integer, Integer>> containerInput;

    public ContainerInput() {
        this.containerInput = new CopyOnWriteArrayList<>();
    }

    public List<Pair<Integer, Integer>> getContainerInput() {
        return containerInput;
    }

    @Override
    public void read(String... args) {
        var fileName = args[0];
        Preconditions.require(fileName != null && fileName.endsWith(".txt"));

        var file = new File(fileName);

        Preconditions.require(file.exists(), "The given file with name " + fileName + " does not exist.");

        try {
            var scanner = new Scanner(file);

            int errorCount = 0;

            while (scanner.hasNext()){
                errorCount++;
                var line = scanner.nextLine();
                try {
                    var heavierContainer = line.split(" ")[0];
                    var lighterContainer = line.split(" ")[1];

                    containerInput.add(new Pair<>(Integer.valueOf(heavierContainer), Integer.valueOf(lighterContainer)));
                }catch (Throwable exception) {
                    System.err.println("Failed to read file! Formatted wrongly in line " + errorCount + " with error:");
                    exception.printStackTrace();
                    System.exit(0);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
