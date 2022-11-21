package de.ytendx.bwinf.tasks.störung;

import de.ytendx.bwinf.io.impl.TextInput;
import de.ytendx.bwinf.tasks.Id;
import de.ytendx.bwinf.tasks.Task;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.regex.Pattern;
@Id(name = "sto")
public class StörungTask implements Task{
    @Override
    public void execute() {
        TextInput input = new TextInput();

        System.out.println("Bitte geben sie die Text file an:");
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String fileName = null;

        try {
            fileName = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Preconditions.require(fileName != null);

        input.read(fileName);

        String command = "";

        while (!command.equals("exit")){
            System.out.println("Gib ein File path an oder 'exit' um weitere textstellen zu vervollständigen oder das programm abzubrechen:");
            try {
                command = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            File file2 = new File(command);

            if (!file2.exists()){
                continue;
            }

            try {
                var data = new String(Files.readAllBytes(file2.toPath()));

                var p = Pattern.compile(data.replaceAll("_", "[a-zA-Z0-9\s]*"), Pattern.CASE_INSENSITIVE);
                var m = p.matcher(input.getText());

                System.out.println("Matched?: " + m.find() + " | Match: " + m.group());
            } catch (Throwable e) {
                // Ignore
                System.out.println("No match found. Maybe wrong file? Try again...");
            }
        }


    }
}
