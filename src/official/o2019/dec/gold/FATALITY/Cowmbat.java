package official.o2019.dec.gold.FATALITY;

import java.io.*;
import java.util.*;

public final class Cowmbat {
    private static final int INVALID = 420696969;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowmbat.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int comboLen = Integer.parseInt(initial.nextToken());
        int buttonNum = Integer.parseInt(initial.nextToken());
        int minStreak = Integer.parseInt(initial.nextToken());
        int[] rnCombo = read.readLine().chars().map(i -> i - 'a').toArray();
        int[][] times = new int[buttonNum][buttonNum];
        for (int b = 0; b < buttonNum; b++) {
            times[b] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        // note: the input isn't perfectly symmetrical, so from key i -> j doesn't always == j -> i
        int[][] minTimes = actualMinTimes(times);
        int[][] switchCosts = new int[buttonNum][comboLen + 1];
        for (int b = 0; b < buttonNum; b++) {
            for (int c = 1; c < comboLen + 1; c++) {  // prefix sum array so range queries can be answered in O(1)
                switchCosts[b][c] = switchCosts[b][c - 1] + minTimes[rnCombo[c - 1]][b];
            }
        }

        // the min amt of days to switch the prev {first index} buttons to valid streaks given the last button number
        int[][] minDaysWithButton = new int[comboLen + 1][buttonNum];
        int[] overallMin = new int[comboLen + 1];
        Arrays.fill(overallMin, INVALID);
        overallMin[0] = 0;

        for (int b = 0; b < buttonNum; b++) {
            for (int c = 0; c < comboLen + 1; c++) {
                minDaysWithButton[c][b] = 0 < c && c < minStreak ? INVALID : switchCosts[b][c];
            }
        }
        
        for (int c = 1; c < comboLen + 1; c++) {
            for (int b = 0; b < buttonNum; b++) {
                // possibility 1- we just extend the previous thing
                int extensionTotal = minDaysWithButton[c - 1][b] + minTimes[rnCombo[c - 1]][b];
                int switchBackTotal = INVALID;
                if (c == minStreak || c >= 2 * minStreak) {
                    // possibility 2- if possible, we change a previous config (c - minStreak) to button b
                    switchBackTotal = overallMin[c - minStreak] + (switchCosts[b][c] - switchCosts[b][c - minStreak]);
                }
                minDaysWithButton[c][b] = Math.min(minDaysWithButton[c][b], Math.min(extensionTotal, switchBackTotal));
                overallMin[c] = Math.min(overallMin[c], minDaysWithButton[c][b]);
            }
        }

        PrintWriter written = new PrintWriter("cowmbat.out");
        written.println(overallMin[comboLen]);
        written.close();
        System.out.println(overallMin[comboLen]);
        System.out.printf("boom bessie pulled off the combo in a mere %d ms%n", System.currentTimeMillis() - start);
    }

    static int[][] actualMinTimes(int[][] times) {
        if (times.length != times[0].length) {
            throw new IllegalArgumentException("wait something's wrong with your distance matrix (thought it should be a square)");
        }
        int nodeNum = times.length;
        int[][] minTimes = new int[nodeNum][nodeNum];
        for (int i = 0; i < nodeNum; i++) {
            minTimes[i] = times[i].clone();
        }
        for (int i = 0; i < nodeNum; i++) {
            for (int j = 0; j < nodeNum; j++) {
                for (int k = 0; k < nodeNum; k++) {
                    minTimes[j][k] = Math.min(minTimes[j][k], minTimes[j][i] + minTimes[i][k]);
                }
            }
        }
        return minTimes;
    }
}
