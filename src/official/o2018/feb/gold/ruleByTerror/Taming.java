package official.o2018.feb.gold.ruleByTerror;

import java.io.*;
import java.util.*;

// 2018 feb gold
public final class Taming {
    // i swear one day the test cases will catch up to me using this variable
    private static final int INVALID = 420696969;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("taming.in"));
        int dayNum = Integer.parseInt(read.readLine());
        int[] log = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        if (dayNum != log.length) {
            throw new IllegalArgumentException(String.format("bro you said there were %d days but you gave me %d days", dayNum, dayNum));
        }

        // this[i][j] = the cost to change nums between i and j inclusive to the format 0,1,...
        int[][] changeRangeCosts = new int[dayNum][dayNum];
        for (int i = 0; i < dayNum; i++) {
            Arrays.fill(changeRangeCosts[i], -1);  // so i don't go half insane while debugging
            int total = 0;
            for (int j = 0; i + j < dayNum; j++) {
                total += log[i + j] != j ? 1 : 0;
                changeRangeCosts[i][i + j] = total;
            }
        }

        // this[i][j] = min cost to change everything before and including index j so there's i breakouts
        int[][] minBreakoutCosts = new int[dayNum + 1][dayNum];
        for (int i = 0; i <= dayNum; i++) {
            Arrays.fill(minBreakoutCosts[i], INVALID);
        }
        minBreakoutCosts[0] = null;  // just making it clear here bc we can't have 0 breakouts
        minBreakoutCosts[1] = changeRangeCosts[0].clone();  // not sure if .clone() is needed but whatever
        // start processing everything onward from 2 breakouts
        for (int i = 2; i <= dayNum; i++) {
            // start from i - 1 because like we can't have 5 breakouts if there's only been 2 days
            for (int j = i - 1; j < dayNum; j++) {
                for (int prev = 0; prev < j; prev++) {
                    // slap on a new breakout to a previous one, and adding the costs and stuff as well
                    minBreakoutCosts[i][j] = Math.min(
                            minBreakoutCosts[i][j],
                            minBreakoutCosts[i - 1][prev] + changeRangeCosts[prev + 1][j]
                    );
                }
            }
        }

        int[] minChanged = new int[dayNum];
        for (int i = 1; i <= dayNum; i++) {
            minChanged[i - 1] = minBreakoutCosts[i][dayNum - 1];
        }
        System.out.println(Arrays.toString(minChanged));
        PrintWriter written = new PrintWriter("taming.out");
        Arrays.stream(minChanged).forEach(written::println);
        written.close();
        System.out.printf("fear will keep them in line: %d ms%n", System.currentTimeMillis() - start);
    }
}
