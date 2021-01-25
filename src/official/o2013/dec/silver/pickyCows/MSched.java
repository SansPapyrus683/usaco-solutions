package official.o2013.dec.silver.pickyCows;

import java.io.*;
import java.util.*;

// 2013 dec silver
public final class MSched {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("msched.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        int maxTime = 0;
        for (int c = 0; c < cowNum; c++) {
            // first the milk amount, then the milk deadline
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            maxTime = Math.max(maxTime, cows[c][1]);
        }
        read.close();

        boolean[] assigned = new boolean[cowNum];
        int[] cowList = new int[maxTime];
        for (int t = maxTime - 1; t >= 0; t--) {
            int cowToAssign = -1;
            int bestCowMilk = 0;
            for (int c = 0; c < cowNum; c++) {
                if (cows[c][1] > t && cows[c][0] > bestCowMilk && !assigned[c]) {
                    bestCowMilk = cows[c][0];
                    cowToAssign = c;
                }
            }

            if (cowToAssign != -1) {
                assigned[cowToAssign] = true;
                cowList[t] = cows[cowToAssign][0];
            }
        }

        int totalMilk = Arrays.stream(cowList).sum();
        PrintWriter written = new PrintWriter("msched.out");
        written.println(totalMilk);
        written.close();
        System.out.println(totalMilk);
        System.out.printf("idk man it took what, %d ms?%n", System.currentTimeMillis() - start);
    }
}
