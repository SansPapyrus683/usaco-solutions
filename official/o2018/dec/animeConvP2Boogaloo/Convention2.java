import java.io.*;
import java.util.*;

// 2018 dec silver
public class Convention2 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("convention2.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][3];
        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            cows[c][0] = Integer.parseInt(cow.nextToken());  // arrival
            cows[c][1] = Integer.parseInt(cow.nextToken());  // time spent
            cows[c][2] = c;  // put the seniority in for sorting
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        PriorityQueue<int[]> inLine = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
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
                if (pastureCow == null) {  // but wait, the pasture's empty! straight to the grass we go!
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

        PrintWriter written = new PrintWriter("convention2.out");
        written.println(maxWait);
        written.close();
        System.out.println(maxWait);
        System.out.printf("jesus frickin' christ, %d ms!%n", System.currentTimeMillis() - start);
    }
}
