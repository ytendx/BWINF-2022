package de.ytendx.bwinf.io.impl;

import de.ytendx.bwinf.io.TaskInput;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PairInput implements TaskInput {

    private final Set<Pair<String, String>> pairs;

    public Set<Pair<String, String>> getPairs() {
        return pairs;
    }

    public PairInput() {
        this.pairs = ConcurrentHashMap.newKeySet();
    }

    @Override
    public void read(String... args) {
        var fileName = args[0];
        Preconditions.require(fileName != null && fileName.endsWith(".csv"));

        var file = new File(fileName);

        Preconditions.require(file.exists(), "The given file with name " + fileName + " does not exist.");

        try {
            var scanner = new Scanner(file);

            int errorCount = 0;

            while (scanner.hasNext()){
                errorCount++;
                var line = scanner.nextLine();

                var splittedLine = line.split(",");

                Preconditions.require(splittedLine.length == 2, "Line number " + errorCount + " did not full fill all requirements (Invalid arg length (No pair))");

                pairs.add(new Pair<>(splittedLine[0], splittedLine[1]));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
