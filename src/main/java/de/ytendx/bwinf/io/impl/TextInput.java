package de.ytendx.bwinf.io.impl;

import de.ytendx.bwinf.io.TaskInput;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

import static de.ytendx.bwinf.utils.BastisKrankerShit.jout;

public class TextInput implements TaskInput {
    public String getText() {
        return text;
    }

    private String text;

    @Override
    public void read(String... args) {
        var fileName = args[0];
        Preconditions.require(fileName != null && fileName.endsWith(".txt"));

        var file = new File(fileName);

        Preconditions.require(file.exists(), "The given file with name " + fileName + " does not exist.");

        try {
            text = new String(Files.readAllBytes(file.toPath()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
