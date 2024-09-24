package official.o2024.feb.bronze;

import java.io.*;
import java.util.*;

/** 2024 feb bronze */
public class MilkExchange {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int bucketNum = Integer.parseInt(initial.nextToken());
        int time = Integer.parseInt(initial.nextToken());
        String dir = read.readLine();
        int[] buckets =
                Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert dir.length() == bucketNum && buckets.length == bucketNum;

        long totalMilk = Arrays.stream(buckets).mapToLong(Integer::valueOf).sum();
        if (dir.matches("([LR])\\1*")) {
            System.out.println(totalMilk);
            return;
        }

        for (int i = 0; i < bucketNum; i++) {
            int next = (i + 1) % bucketNum;
            if (dir.charAt(i) == 'R' && dir.charAt(next) == 'L') {
                long total = 0;
                int at = (i - 1 + bucketNum) % bucketNum;
                while (dir.charAt(at) != 'L') {
                    total += buckets[at];
                    at = (at - 1 + bucketNum) % bucketNum;
                }
                totalMilk -= Math.min(total, time);
            } else if (dir.charAt(i) == 'L' && dir.charAt(next) == 'R') {
                long total = 0;
                int at = i;
                while (dir.charAt(at) != 'R') {
                    total += buckets[at];
                    at = (at - 1 + bucketNum) % bucketNum;
                }
                total -= buckets[(at + 1) % bucketNum];
                totalMilk -= Math.min(total, time);
            }
        }

        System.out.println(totalMilk);
    }
}
