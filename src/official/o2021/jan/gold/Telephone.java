package official.o2021.jan.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 jan gold
 * 5 4
 * 1 4 2 3 4
 * 1010
 * 0001
 * 0110
 * 0100 should output 6
 */
public class Telephone {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int typeNum = Integer.parseInt(initial.nextToken());

        // all types are 1-indexed (dw it won't affect how we calculate the costs)
        int[] types = Arrays.stream(read.readLine().split(" ")).mapToInt(t -> Integer.parseInt(t) - 1).toArray();
        ArrayList<Integer>[] cowsOfType = new ArrayList[typeNum];
        for (int t = 0; t < typeNum; t++) {
            cowsOfType[t] = new ArrayList<>();
        }
        for (int i = 0; i < cowNum; i++) {
            cowsOfType[types[i]].add(i);
        }

        ArrayList<Integer>[] communicatable = new ArrayList[typeNum];
        for (int t = 0; t < typeNum; t++) {
            communicatable[t] = new ArrayList<>();
            String others = read.readLine();
            for (int ot = 0; ot < typeNum; ot++) {
                if (others.charAt(ot) == '1') {
                    communicatable[t].add(ot);
                }
            }
        }

        long start = System.currentTimeMillis();
        boolean[] visited = new boolean[cowNum];  // so apparently you can keep a visited array in dijkstra's as well
        int[] minTime = new int[cowNum];
        Arrays.fill(minTime, Integer.MAX_VALUE);
        minTime[0] = 0;
        PriorityQueue<Integer> frontier = new PriorityQueue<>(Comparator.comparingInt(c -> minTime[c]));
        frontier.add(0);
        while (!frontier.isEmpty()) {
            int curr = frontier.poll();
            int rnCost = minTime[curr];
            if (visited[curr]) {
                continue;
            }
            visited[curr] = true;
            for (int t : communicatable[types[curr]]) {
                for (int n : cowsOfType[t]) {
                    int newCost = rnCost + Math.abs(curr - n);
                    if (newCost < minTime[n]) {
                        minTime[n] = newCost;
                        frontier.add(n);
                    }
                }
            }
        }
        int leastTime = minTime[cowNum - 1] == Integer.MAX_VALUE ? -1 : minTime[cowNum - 1];
        System.out.println(leastTime);
        System.err.printf("bruh how does fj manage to have 50 types of cows: %d ms", System.currentTimeMillis() - start);
    }
}
