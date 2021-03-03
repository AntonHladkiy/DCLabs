package c;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int monksCount = 100;
        int[] monksPower = new int[monksCount];
        for (int i = 0; i < monksCount; i++) {
            monksPower[i] = i;
        }

        Random random = new Random();
        for (int i = 0; i < monksCount; i++) {
            int index1 = random.nextInt(100);
            int index2 = random.nextInt(100);
            int temp = monksPower[index1];
            monksPower[index1] = monksPower[index2];
            monksPower[index2] = temp;
        }

        int expectedWinnerIndex = 0;
        int maxQiEnergy = 0;
        for (int i = 0; i < monksCount; i++) {
            if (monksPower[i] > maxQiEnergy) {
                expectedWinnerIndex = i;
                maxQiEnergy = monksPower[i];
            }
        }

        Duel.setMonksPower(monksPower);
        Duel duel = new Duel(0, monksPower.length-1);

        int winnerIndex = duel.compute();
        if (winnerIndex == expectedWinnerIndex)
            System.out.println("Success");
    }
}
