package official.o2017.feb.bronze.KFCow3;

import java.io.*;
import java.util.*;

// 2017 feb bronze
public final class CowQueue {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowqueue.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        // i copied this from my convention 2 code lol (2018 dec silver)
        ArrayDeque<int[]> inLine = new ArrayDeque<>();
        int time = cows[0][0];  // global timer
        int interrogationStart = cows[0][0];  // the time that the cow started to get interrogated
        int cowAt = 1;  // the cow that's still wandering around
        int[] borderCow = cows[0];
        while (cowAt < cows.length || borderCow != null) {
            int eatingFinish = borderCow != null ? borderCow[1] - (time - interrogationStart) : Integer.MAX_VALUE;
            int nextCow = cowAt < cows.length ? cows[cowAt][0] - time : Integer.MAX_VALUE;

            if (nextCow <= eatingFinish) {  // ok a cow arrived first
                inLine.add(cows[cowAt]);  // store the arrival time
                time = cows[cowAt][0];
                // but wait, the checkpoint's empty! we can go straight to
                // answering questions about our sleep paralysis demons!
                if (borderCow == null) {
                    borderCow = inLine.poll();
                    interrogationStart = time;
                }
                cowAt++;
            }
            if (eatingFinish <= nextCow) {  // ok the eating event finished first
                time = interrogationStart + borderCow[1];
                int[] nextUp = inLine.poll();
                borderCow = nextUp;
                interrogationStart = time;
            }
        }

        PrintWriter written = new PrintWriter("cowqueue.out");
        written.println(time);
        written.close();
        System.out.println(time);
        System.out.printf("GLORY TO FARMER JOHN. (%d ms)", System.currentTimeMillis() - start);
    }
}
