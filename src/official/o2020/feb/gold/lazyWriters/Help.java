package official.o2020.feb.gold.lazyWriters;

import java.io.*;
import java.util.*;

public class Help {
    private static final int MOD = (int) Math.pow(10, 9) + 7;
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("help.in"));
        int intervalNum = Integer.parseInt(read.readLine());
        final long[] twoPow = new long[intervalNum + 1];
        twoPow[0] = 1;
        for (int i = 1; i <= intervalNum; i++) {
            twoPow[i] = twoPow[i - 1] * 2 % MOD;
        }

        int[][] intervals = new int[intervalNum][2];
        int start = Integer.MAX_VALUE;
        for (int i = 0; i < intervalNum; i++) {
            intervals[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            start = Math.min(start, intervals[i][0]);
            if (intervals[i][0] >= intervals[i][1]) {
                throw new IllegalArgumentException(String.format("the interval %s is invalid", Arrays.toString(intervals[i])));
            }
        }
        for (int[] i : intervals) {  // have the intervals start from 1
            i[0] -= start - 1;
            i[1] -= start - 1;
        }
        int end = Arrays.stream(intervals).mapToInt(i -> i[1]).max().getAsInt();

        int[] segmentNum = new int[end + 1];
        for (int[] i : intervals) {
            segmentNum[i[0]]++;
            segmentNum[i[1]]--;
        }
        for (int i = 1; i <= end; i++) {
            segmentNum[i] += segmentNum[i - 1];
        }

        long totalComplexity = 0;
        for (int[] i : intervals) {
            totalComplexity = (totalComplexity + twoPow[intervalNum - 1 - segmentNum[i[0] - 1]]) % MOD;
        }
        PrintWriter written = new PrintWriter("help.out");
        written.println(totalComplexity);
        written.close();
        System.out.println(totalComplexity);
        System.out.printf("this flavor text is just lazy: %d ms%n", System.currentTimeMillis() - timeStart);
    }
}
