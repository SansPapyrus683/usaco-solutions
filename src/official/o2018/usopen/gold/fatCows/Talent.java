package official.o2018.usopen.gold.fatCows;

import java.io.*;
import java.util.*;

// 2018 usopen gold
public final class Talent {
    private static final int INVALID = 420696969;
    private static final int MUL_BY = 1000;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("talent.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int weightReq = Integer.parseInt(initial.nextToken());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            // first weight, then talent
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        // sauce: https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
        // bc weight is too large, let's dp on the talent instead
        int totalTalent = Arrays.stream(cows).mapToInt(c -> c[1]).sum();
        int[][] minWeights = new int[cowNum + 1][totalTalent + 1];
        for (int c = 0; c < cowNum; c++) {
            Arrays.fill(minWeights[c], INVALID);
        }
        minWeights[0][0] = 0;
        for (int c = 1; c <= cowNum; c++) {
            int[] thisCow = cows[c - 1];
            for (int t = 0; t <= totalTalent; t++) {
                if (t == 0) {
                    minWeights[c][t] = 0;
                } else if (thisCow[1] <= t) {
                    minWeights[c][t] = Math.min(
                            thisCow[0] + minWeights[c - 1][t - thisCow[1]],
                            minWeights[c - 1][t]
                    );
                } else {
                    minWeights[c][t] = minWeights[c - 1][t];
                }
            }
        }

        double bestScore = 0;
        for (int c = 1; c <= cowNum; c++) {
            for (int t = 0; t <= totalTalent; t++) {
                if (minWeights[c][t] >= weightReq) {
                    bestScore = Math.max(bestScore, (double) t / minWeights[c][t]);
                }
            }
        }

        PrintWriter written = new PrintWriter("talent.out");
        written.println((int) (bestScore * MUL_BY));  // why not just ask for the truncated decimal lol
        written.close();
        System.out.println((int) (bestScore * MUL_BY));
        System.out.printf("i swear one day i will find benq and strangle him: %d ms%n", System.currentTimeMillis() - start);
    }
}
