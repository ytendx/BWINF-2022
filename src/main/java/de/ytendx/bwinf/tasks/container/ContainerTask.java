package de.ytendx.bwinf.tasks.container;

import de.ytendx.bwinf.io.impl.ContainerInput;
import de.ytendx.bwinf.tasks.Id;
import de.ytendx.bwinf.tasks.Task;
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
public class ContainerTask implements Task<ContainerInput> {
    @Override
    public void execute(ContainerInput input) {
        System.out.println("Bitte gebe die container file an:");
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String fileName = null;

        try {
            fileName = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Preconditions.require(fileName != null);

        input.read(fileName);

        var output = new int[this.getMaxContainer(input)];

        var currentIndex = 0;

        List<Integer> alreadySortedIDs = new CopyOnWriteArrayList<>();

        for (int i = 0; i < output.length; i++){
            var lighter = heavierThenTheOthers(i+1, input);

            System.out.println(i+1 + " is heavier then " + Arrays.toString(lighter.toArray()));

            if (lighter.size() == 0){
                output[currentIndex] = i+1;
                currentIndex++;
            }
        }

        for (int i = 0; i < output.length; i++){
            var lighter = heavierThenTheOthers(i+1, input);

            check(i+1, input, currentIndex, output, lighter, alreadySortedIDs);
        }

        System.out.println("End: " + Arrays.toString(output));
    }

    public void check(int container, ContainerInput input, int currentIndex, int[] output, List<Integer> lighter, List<Integer> alreadySortedIDs){
        for (int light : lighter) {
            var lightLighter = heavierThenTheOthers(light, input);

            check(light, input, currentIndex, output, lightLighter, alreadySortedIDs);
        }

        var max = getMax(Arrays.copyOf(lighter.toArray(), lighter.size(), Integer[].class));

        if (!alreadySortedIDs.contains(container)){
            if (max == 0){
                output[currentIndex] = container;
                currentIndex++;
                alreadySortedIDs.add(container);
                return;
            }

            output[getMax(Arrays.copyOf(lighter.toArray(), lighter.size(), Integer[].class))-1] = container;
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
