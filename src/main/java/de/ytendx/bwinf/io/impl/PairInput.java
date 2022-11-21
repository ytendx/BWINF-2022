package de.ytendx.bwinf.io.impl;

import de.ytendx.bwinf.io.TaskInput;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
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
        Preconditions.require(fileName != null && fileName.endsWith(".txt"));

        var file = new File(fileName);

        Preconditions.require(file.exists(), "The given file with name " + fileName + " does not exist.");

        try {
            var content = new String(Files.readAllBytes(file.toPath()));

            for(var line : content.split("\n")) {
                for(var line2 : content.split("\n")) {
                    if (line.equals(line2)){
                        continue;
                    }

                    pairs.add(new Pair<>(line, line2));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
