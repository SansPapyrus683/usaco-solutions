package official.o2020.dec.silver;

import java.io.*;
import java.util.*;

/**
 * 2020 dec silver
 * 4
 * 0 2
 * 1 0
 * 2 3
 * 3 5 should output 13
 */
public class RPasture {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        
        int cowNum = Integer.parseInt(read.readLine());
        HashSet<Integer> seenX = new HashSet<>();
        HashSet<Integer> seenY = new HashSet<>();
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            assert !seenX.contains(cows[c][0]) && !seenY.contains(cows[c][1]);
            seenX.add(cows[c][0]);
            seenY.add(cows[c][1]);
        }

        // we do a little coordinate compression
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));  // sort by x
        HashMap<Integer, Integer> reducedX = new HashMap<>();
        for (int c = 0; c < cowNum; c++) {
            reducedX.put(cows[c][0], c);
        }

        Arrays.sort(cows, Comparator.comparingInt(c -> c[1]));
        HashMap<Integer, Integer> reducedY = new HashMap<>();  // sort by y
        for (int c = 0; c < cowNum; c++) {
            reducedY.put(cows[c][1], c);
        }

        for (int c = 0; c < cowNum; c++) {
            cows[c][0] = reducedX.get(cows[c][0]);
            cows[c][1] = reducedY.get(cows[c][1]);
        }

        // sort by x again
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));
        // make our prefix sums for the y-lines
        int[][] ltY = new int[cowNum][cowNum + 1];
        int[][] gtY = new int[cowNum][cowNum + 1];
        for (int c = 0; c < cowNum; c++) {
            int currY = cows[c][1];
            ltY[currY] = new int[cowNum + 1];
            gtY[currY] = new int[cowNum + 1];
            for (int x = 1; x <= cowNum; x++) {
                ltY[currY][x] = (
                    ltY[currY][x - 1] + (cows[x - 1][1] < currY ? 1 : 0)
                );
                gtY[currY][x] = (
                    gtY[currY][x - 1] + (cows[x - 1][1] > currY ? 1 : 0)
                );
            }
        }

        long total = 0;
        for (int c1 = 0; c1 < cowNum; c1++) {
            for (int c2 = c1 + 1; c2 < cowNum; c2++) {
                int bottom = Math.min(cows[c1][1], cows[c2][1]);
                int top = Math.max(cows[c1][1], cows[c2][1]);

                int bottomTotal = 1 + ltY[bottom][c2 + 1] - ltY[bottom][c1];
                int topTotal = 1 + gtY[top][c2 + 1] - gtY[top][c1];

                total += (long) bottomTotal * topTotal;
            }
        }

        /*
         * we didn't count the ones where fj just boxes
         * either a single cow or no cow at all
         */
        total += cowNum + 1;
        System.out.println(total);
        System.err.printf("time: %d ms%n", System.currentTimeMillis() - start);
    }
}
