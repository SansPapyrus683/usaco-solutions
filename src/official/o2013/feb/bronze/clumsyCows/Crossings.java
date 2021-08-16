package official.o2013.feb.bronze.clumsyCows;

import java.io.*;
import java.util.*;

// most of my sols are structured like this (bare bones)
public final class Crossings {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("crossings.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] routes = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            routes[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(routes, (r1, r2) -> r1[0] != r2[0] ? r1[0] - r2[0] : r1[1] - r2[1]);

        boolean[] safe = new boolean[cowNum];
        Arrays.fill(safe, true);
        int rightestSoFar = Integer.MIN_VALUE;
        for (int c = 0; c < cowNum; c++) {
            if (routes[c][1] < rightestSoFar) {
                safe[c] = false;
            }
            rightestSoFar = Math.max(rightestSoFar, routes[c][1]);
        }
        int leftestSoFar = Integer.MAX_VALUE;
        for (int c = cowNum - 1; c >= 0; c--) {
            if (routes[c][1] > leftestSoFar) {
                safe[c] = false;
            }
            leftestSoFar = Math.min(leftestSoFar, routes[c][1]);
        }

        int safeNum = 0;
        for (boolean s : safe) {
            safeNum += s ? 1 : 0;
        }

        PrintWriter written = new PrintWriter("crossings.out");
        written.println(safeNum);
        written.close();
        System.out.println(safeNum);
        System.out.printf("these cows are awful i hate them so much: %d ms%n", System.currentTimeMillis() - start);
    }
}
