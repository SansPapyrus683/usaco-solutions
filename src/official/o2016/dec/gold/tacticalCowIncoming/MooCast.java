package official.o2016.dec.gold.tacticalCowIncoming;

import java.io.*;
import java.util.*;

// 2016 dec gold
public final class MooCast {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("moocast.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int upperBound = Integer.MAX_VALUE;  // too lazy to calculate the actual upper bound
        int lowerBound = 0;
        int valid = -1;
        while (lowerBound <= upperBound) {
            int toSearch = upperBound / 2 + lowerBound / 2;
            if (allReachable(toSearch, cows)) {
                valid = toSearch;
                upperBound = toSearch - 1;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter("moocast.out");
        written.println(valid);
        written.close();
        System.out.println(valid);
        System.out.printf("code red: it took %d ms%n", System.currentTimeMillis() - start);
    }

    static boolean allReachable(int power, int[][] cows) {
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(0));
        boolean[] reached = new boolean[cows.length];
        reached[0] = true;
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            for (int c = 0; c < cows.length; c++) {
                if (!reached[c] && distance(cows[curr], cows[c]) <= power) {
                    frontier.add(c);
                    reached[c] = true;
                }
            }
        }
        for (boolean c : reached) {
            if (!c) {
                return false;
            }
        }
        return true;
    }

    static int distance(int[] c1, int[] c2) {
        return (int) Math.pow(c1[0] - c2[0], 2) + (int) Math.pow(c1[1] - c2[1], 2);
    }
}

