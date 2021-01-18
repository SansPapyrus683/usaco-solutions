package official.o2017.feb.bronze.KFCow3;

import java.io.*;
import java.util.*;

// 2017 feb bronze
public class CowQueue {
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
        int eatingStart = cows[0][0];  // the time that the cow started to eat
        int cowAt = 1;  // the cow that's still wandering around
        int maxWait = 0;
        int[] pastureCow = cows[0];
        while (cowAt < cows.length || pastureCow != null) {
            int eatingFinish = pastureCow != null ? pastureCow[1] - (time - eatingStart) : Integer.MAX_VALUE;
            int nextCow = cowAt < cows.length ? cows[cowAt][0] - time : Integer.MAX_VALUE;

            if (nextCow <= eatingFinish) {  // ok a cow arrived first
                inLine.add(cows[cowAt]);  // store the arrival time
                time = cows[cowAt][0];
                // but wait, the checkpoint's empty! we can go straight to
                // answering questions about our sleep paralysis demons!
                if (pastureCow == null) {
                    pastureCow = inLine.poll();
                    eatingStart = time;
                }
                cowAt++;
            }
            if (eatingFinish <= nextCow) {  // ok the eating event finished first
                time = eatingStart + pastureCow[1];
                int[] nextUp = inLine.poll();
                pastureCow = nextUp;
                if (nextUp != null) {  // there's a chance the queue is empty
                    maxWait = Math.max(maxWait, time - nextUp[0]);
                }
                eatingStart = time;
            }
        }

        PrintWriter written = new PrintWriter("cowqueue.out");
        written.println(time);
        written.close();
        System.out.println(time);
        System.out.printf("GLORY TO FARMER JOHN. (%d ms)", System.currentTimeMillis() - start);
    }
}
