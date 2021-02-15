package official.o2018.dec.gold.communism;

import java.io.*;
import java.util.*;

// 2018 dec gold
public final class Teamwork {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("teamwork.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int maxSize = Integer.parseInt(initial.nextToken());
        int[] cows = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Integer.parseInt(read.readLine());
        }

        int[] maxSkill = new int[cowNum + 1];
        maxSkill[0] = 0;
        for (int i = 1; i <= cowNum; i++) {
            int runningMaxSkill = cows[i - 1];  // redundant, but whatever
            maxSkill[i] = runningMaxSkill;
            // build a team off the previous ones
            for (int prev = i - 1; prev >= Math.max(i - maxSize, 0); prev--) {
                runningMaxSkill = Math.max(runningMaxSkill, cows[prev]);
                // with the new max skill, slap the new team onto the previous ones
                maxSkill[i] = Math.max(maxSkill[i], maxSkill[prev] + (i - prev) * runningMaxSkill);
            }
        }

        PrintWriter written = new PrintWriter("teamwork.out");
        written.println(maxSkill[cowNum]);
        written.close();
        System.out.println(maxSkill[cowNum]);
        System.out.printf("comrade, we must all contribute to the well-being of the farm: %d ms%n", System.currentTimeMillis() - start);
    }
}
