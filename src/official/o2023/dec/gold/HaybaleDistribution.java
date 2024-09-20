package official.o2023.dec.gold;

import java.io.*;
import java.util.*;

/**
 * 2023 dec gold
 * <pre>
 * 5
 * 1 4 2 3 10
 * 4
 * 1 1
 * 2 1
 * 1 2
 * 1 4 should output 11, 13, 18, and 30, each on a new line
 * </pre>
 */
public class HaybaleDistribution {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int barnNum = Integer.parseInt(read.readLine());
        int[] barns =
                Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        assert barns.length == barnNum;
        Arrays.sort(barns);

        long[] left = new long[barnNum];
        long[] right = new long[barnNum];
        for (int b : barns) {
            right[0] += b - barns[0];
        }
        for (int i = 1; i < barnNum; i++) {
            int delta = barns[i] - barns[i - 1];
            left[i] = left[i - 1] + (long) delta * i;
            right[i] = right[i - 1] - (long) delta * (barnNum - i);
        }

        int queryNum = Integer.parseInt(read.readLine());
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            // uh possible overflow in the multiplication below, and i'm too lazy to cast
            long incCost = Integer.parseInt(query.nextToken());
            long decCost = Integer.parseInt(query.nextToken());

            long cSum = incCost + decCost;
            int lb = (int) ((double) (decCost * barnNum - cSum) / cSum);
            int ub = (int) Math.ceil((double) decCost * barnNum / cSum);

            long bestCost = Long.MAX_VALUE;
            for (int i = Math.max(lb, 0); i <= Math.min(ub, barnNum - 1); i++) {
                bestCost = Math.min(bestCost, incCost * left[i] + decCost * right[i]);
            }

            System.out.println(bestCost);
        }
    }
}
