package official.o2016.feb.silver.bigBrainFJ;

import java.io.*;
import java.util.*;

// 2016 feb silver
public final class Pails {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("pails.in"));
        StringTokenizer info = new StringTokenizer(read.readLine());
        int firstPail = Integer.parseInt(info.nextToken());
        int secondPail = Integer.parseInt(info.nextToken());
        int opNum = Integer.parseInt(info.nextToken());
        int target = Integer.parseInt(info.nextToken());

        boolean[][] madeBefore = new boolean[firstPail + 1][secondPail + 1];  // max 100m states, not too bad
        madeBefore[0][0] = true;
        ArrayList<int[]> frontier = new ArrayList<>(Collections.singletonList(new int[] {0, 0}));
        int minError = target;
        for (int i = 0; i < opNum; i++) {  // only do so many operations
            ArrayList<int[]> inLine = new ArrayList<>();
            for (int[] p : frontier) {  // expand all the states equally
                for (int[] n : opResults(p, firstPail, secondPail)) {
                    if (!madeBefore[n[0]][n[1]]) {  // don't add it if we attained it already
                        minError = Math.min(minError, Math.abs(n[0] + n[1] - target));
                        madeBefore[n[0]][n[1]] = true;
                        inLine.add(n);
                    }
                }
            }
            frontier = inLine;
        }

        PrintWriter written = new PrintWriter("pails.out");
        written.println(minError);
        written.close();
        System.out.println(minError);
        System.out.printf("i know it may not seem like it but your program took %d ms%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> opResults(int[] initial, int firstCap, int secondCap) {
        ArrayList<int[]> results = new ArrayList<>(Arrays.asList(new int[][] {
                {initial[0], secondCap}, {firstCap, initial[1]},  // top the pails
                {0, initial[1]}, {initial[0], 0}  // we can also empty either
        }));
        int second = Math.min(initial[1] + initial[0], secondCap);  // pour first into second
        int first = initial[0] - (second - initial[1]);
        results.add(new int[] {first, second});

        first = Math.min(initial[0] + initial[1], firstCap);  // pour second into first
        second = initial[1] - (first - initial[0]);
        results.add(new int[] {first, second});
        return results;
    }
}
