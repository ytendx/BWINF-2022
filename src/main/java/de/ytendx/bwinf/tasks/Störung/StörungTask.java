package de.ytendx.bwinf.tasks.Störung;

import de.ytendx.bwinf.io.TaskInput;
import de.ytendx.bwinf.io.impl.PairInput;
import de.ytendx.bwinf.io.impl.TextInput;
import de.ytendx.bwinf.tasks.Id;
import de.ytendx.bwinf.tasks.Task;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import static de.ytendx.bwinf.utils.BastisKrankerShit.jout;

@Id(name = "sto")
public class StörungTask implements Task<TextInput>{
    @Override
    public void execute(TextInput input) {
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
            try {
                command = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var p = Pattern.compile("das$ mir$ ^vor");
            var m = p.matcher("ich sollte nicht da sein. das kommt mir gar nicht richtig vor lol omg krasser shit");

            System.out.println(m.find() + m.group());

        }


    }
}
