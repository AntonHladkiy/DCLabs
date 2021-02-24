package c;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class Duel extends RecursiveTask<Integer> {
    @Setter
    private static int[] monksPower;

    private final int leftIndex;
    private final int rightIndex;

    @Override
    protected Integer compute() {
        if (rightIndex - leftIndex == 1) {
            return getWinnerFromDuel(leftIndex, rightIndex);
        }
        if(leftIndex == rightIndex){
            return leftIndex;
        }
        int middleIndex = (leftIndex + rightIndex)/2;
        Duel leftPartCompetition = new Duel(leftIndex, middleIndex);
        Duel rightPartCompetition = new Duel(middleIndex+1, rightIndex);
        leftPartCompetition.fork();
        rightPartCompetition.fork();
        int leftCompetitionWinnerIndex = leftPartCompetition.join();
        int rightCompetitionWinnerIndex = rightPartCompetition.join();
        int winner = getWinnerFromDuel(leftCompetitionWinnerIndex, rightCompetitionWinnerIndex);
        System.out.println( monksPower[winner]);
        return winner;
    }

    private int getWinnerFromDuel(int index1, int index2){
        return monksPower[index1] > monksPower[index2] ? index1 : index2;
    }
}