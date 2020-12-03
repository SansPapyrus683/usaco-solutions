package official.o2014.dec.silver.piggypiggypiggy;

import java.io.*;
import java.util.*;

public class Piggyback {
    static int farmNum;
    static ArrayList<Integer>[] neighbors;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("piggyback.in"));
        String[] initialInfo = read.readLine().split(" ");
        // screw you usaco, i'm going to do 0-based indexing
        int bessieEnergy = Integer.parseInt(initialInfo[0]);  // bessie starts @ 0
        int elsieEnergy = Integer.parseInt(initialInfo[1]);  // elsie starts @ 1
        int togetherEnergy = Integer.parseInt(initialInfo[2]);
        farmNum = Integer.parseInt(initialInfo[3]);  // farm is at the last thing
        int connectionsNum = Integer.parseInt(initialInfo[4]);
        neighbors = new ArrayList[farmNum];
        for (int i = 0; i < farmNum; i++) {
            neighbors[i] = new ArrayList<>();
        }

        for (int i = 0; i < connectionsNum; i++) {
            String[] unparsed = read.readLine().split(" ");
            int[] relation = new int[] {Integer.parseInt(unparsed[0]) - 1,
                                        Integer.parseInt(unparsed[1]) - 1};
            neighbors[relation[0]].add(relation[1]);
            neighbors[relation[1]].add(relation[0]);
        }

        int best = Integer.MAX_VALUE;
        int[] toFarmDists = pointDists(farmNum - 1);
        int[] bessieDists = pointDists(0);
        int[] elsieDists = pointDists(1);
        for (int i = 0; i < farmNum; i++) {
            best = Math.min(best, bessieDists[i] * bessieEnergy +
                                  elsieDists[i] * elsieEnergy +
                                  toFarmDists[i] * togetherEnergy);
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("piggyback.out"));
        written.println(best);
        written.close();
        System.out.println(best);
        System.out.printf("ok took about %d ms", System.currentTimeMillis() - start);
    }

    static int[] pointDists(int start) {
        int[] closest = new int[farmNum];
        Arrays.fill(closest, Integer.MAX_VALUE - 1000000);  // 1 mil buffer for overflow
        closest[start] = 0;

        ArrayDeque<Integer> frontier = new ArrayDeque<>();
        frontier.add(start);
        while (!frontier.isEmpty()) {
            int current = frontier.poll();
            for (int n : neighbors[current]) {
                if (closest[current] + 1 < closest[n]) {
                    closest[n] = closest[current] + 1;
                    frontier.add(n);
                }
            }
        }

        return closest;
    }
}
