package de.ytendx.bwinf.tasks.reimerei;

import de.ytendx.bwinf.io.TaskInput;
import de.ytendx.bwinf.io.impl.PairInput;
import de.ytendx.bwinf.tasks.Id;
import de.ytendx.bwinf.tasks.Task;
import de.ytendx.bwinf.utils.Pair;
import de.ytendx.bwinf.utils.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


@Id(name = "reimerei")
public class ReimereiTask implements Task {
    private static final List<Character> VOCALS = List.of('a', 'e', 'i', 'o', 'u', 'ä', 'ö', 'ü');

    @Override
    public void execute() {
        PairInput input = new PairInput();

        System.out.println("Bitte gebe die leider keine csv file an:");
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String fileName = null;

        try {
            fileName = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Preconditions.require(fileName != null);

        input.read(fileName);

        var output = input.getPairs().stream().filter(pair -> {
            var aGroups = this.getVocalGroups(pair.getA());
            var bGroups = this.getVocalGroups(pair.getB());

            if(aGroups.size() == 0 || bGroups.size() == 0){
                return false;
            }

            var aGroup = vocalGroup(aGroups);
            var bGroup = vocalGroup(bGroups);

            var check1 = aGroup.getA().equals(bGroup.getA());

            var endA = aGroup.getA() + pair.getA().substring(aGroup.getB());
            var endB = bGroup.getA() + pair.getB().substring(bGroup.getB());

            var check2 = matchingLetters(endA, endB) > endA.length()/2 && matchingLetters(endA, endB) > endB.length()/2;

            var check3 = !pair.getA().endsWith(pair.getB()) && !pair.getB().endsWith(pair.getA());

            return check1 && check2 && check3;
        }).toList();

        System.out.println("\n\n------------------------------------------------------");
        System.out.println("ERGEBNISSE");
        System.out.println("------------------------------------------------------\n");

        output.forEach(stringStringPair -> {
            System.out.println(stringStringPair.getA() + "," + stringStringPair.getB());
        });

    }

    private int matchingLetters(String a, String b){
        int matching = 0;

        for(char charA : a.toCharArray()){
            for(char charB : b.toCharArray()){
                if(charA == charB){
                    matching++;
                }
            }
        }

        return matching;
    }


    private Pair<String, Integer> vocalGroup(Map<String, Integer> vocalGroups){
        if(vocalGroups.size() == 1){
            var s = vocalGroups.keySet().stream().findFirst().get();

            return new Pair<>(s, vocalGroups.get(s));
        }

        var stringOptional = vocalGroups.keySet().stream().skip(vocalGroups.size() - 2).findFirst();

        Preconditions.require(stringOptional.isPresent());

        return new Pair<>(stringOptional.get(), vocalGroups.get(stringOptional.get()));
    }

    private Map<String, Integer> getVocalGroups(String input){
        final Map<String, Integer> output = new ConcurrentHashMap<>();
        StringBuilder current = new StringBuilder();

        int pos = 0;

        for(char curr : input.toCharArray()){
            if(VOCALS.contains(curr)) {
                current.append(curr);
                continue;
            }

            if(!current.isEmpty()){
                output.put(current.toString(), pos);
                current = new StringBuilder();
            }

            pos++;
        }

        return output;
    }
}
