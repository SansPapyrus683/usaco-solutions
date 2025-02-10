package official.o2025.jan.silver;

import java.io.*;
import java.util.*;

/** 2025 jan silver */
public class FaveOp {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            final int len = Integer.parseInt(initial.nextToken());
            final int mod = Integer.parseInt(initial.nextToken());
            TreeMap<Integer, Integer> rems1 = new TreeMap<>();
            TreeMap<Integer, Integer> rems2 = new TreeMap<>();
            StringTokenizer arr = new StringTokenizer(read.readLine());
            for (int i = 0; i < len; i++) {
                int r = Integer.parseInt(arr.nextToken()) % mod;
                rems1.put(r, rems1.getOrDefault(r, 0) + 1);
                rems2.put(mod - r, rems2.getOrDefault(mod - r, 0) + 1);
            }

            long[] cost1 = leftSide(rems1, mod);
            long[] cost2 = leftSide(rems2, mod);
            long best = Long.MAX_VALUE;
            for (int i = 0; i < cost1.length; i++) {
                best = Math.min(best, cost1[i] + cost2[cost2.length - i - 1]);
            }
            System.out.println(best);
        }
    }

    private static long[] leftSide(TreeMap<Integer, Integer> remMap, int mod) {
        int lastX = remMap.firstKey();
        int trailNum = 0;
        int windowNum = 0;
        long cost = 0;
        long[] allCosts = new long[remMap.size()];
        int at = 0;
        for (Map.Entry<Integer, Integer> e : remMap.entrySet()) {
            final int x = e.getKey();
            cost -= (long) (x - lastX) * trailNum;

            SortedMap<Integer, Integer> what = remMap.subMap(lastX - mod / 2, x - mod / 2);
            for (Map.Entry<Integer, Integer> done : what.entrySet()) {
                final int freq = done.getValue();
                windowNum -= freq;
                trailNum += freq;
                cost -= ops(lastX, done.getKey(), mod) * freq;
                cost += ops(x, done.getKey(), mod) * freq;
            }
            cost += (long) (x - lastX) * windowNum;

            windowNum += e.getValue();

            lastX = x;
            allCosts[at++] = cost;
        }

        return allCosts;
    }

    private static long ops(int zero, int at, int mod) {
        long add = (zero - at + mod) % mod;
        long subtract = (at - zero + mod) % mod;
        return Math.min(add, subtract);
    }
}
