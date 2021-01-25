package official.o2016.jan.silver.FJPTSD;

import java.io.*;
import java.util.*;

// 2016 jan silver (the premise of this problem is the most questionable one so far)
public final class Div7 {
    private static final int MOD = 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("div7.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int maxLen = 0;
        int[] firstModOccurrence = new int[MOD];
        Arrays.fill(firstModOccurrence, -2);
        firstModOccurrence[0] = -1;
        int runningMod = 0;
        /* 
         * so if the running total at index a is the same as the running total at index b, then
         * we can say that the total between a and b is divisible by 7
         * of course if the running total is 0 then the start will always be at the start of the array
         */
        for (int c = 0; c < cowNum; c++) {
            int cow = Integer.parseInt(read.readLine());
            runningMod = (runningMod + cow) % MOD;
            if (firstModOccurrence[runningMod] == -2) {
                firstModOccurrence[runningMod] = c;
            } else {
                maxLen = Math.max(maxLen, c - firstModOccurrence[runningMod]);
            }
        }

        PrintWriter written = new PrintWriter("div7.out");
        written.println(maxLen);
        written.close();
        System.out.println(maxLen);
        System.out.printf("wow... %d ms... lol%n", System.currentTimeMillis() - start);
    }
}
