package official.o2014.dec.silver.swoleCows;

import java.io.*;
import java.util.*;

// 2014 dec silver (this one implements the "intended" sol)
public class CowJog {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowjog.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int[][] cows = new int[cowNum][2];
        int time = Integer.parseInt(initial.nextToken());
        for (int c = 0; c < cows.length; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));
        // the position that the cow will finish at if it has no obstructions
        long[] finishLines = new long[cows.length];
        for (int c = 0; c < cowNum; c++) {
            finishLines[c] = cows[c][0] + (long) time * cows[c][1];
        }

        int groupNum = 1;  // there will always be one group
        long slowingLine = finishLines[cowNum - 1];  // the finish line of the current group
        for (int i = cowNum - 1; i >= 0; i--) {
            if (finishLines[i] < slowingLine) {
                groupNum++;
            }
            slowingLine = Math.min(slowingLine, finishLines[i]);
        }

        PrintWriter written = new PrintWriter("cowjog.out");
        written.println(groupNum);
        written.close();
        System.out.println(groupNum);
        System.out.printf("ok so it took around %d ms%n", System.currentTimeMillis() - start);
    }
}
