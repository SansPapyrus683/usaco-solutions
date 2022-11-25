package official.o2018.dec.gold.blandHay;

import java.io.*;
import java.util.*;

// 2018 dec gold
public class Dining {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("dining.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int pastureNum = Integer.parseInt(initial.nextToken());
        int pathNum = Integer.parseInt(initial.nextToken());
        int hayNum = Integer.parseInt(initial.nextToken());

        ArrayList<int[]>[] neighbors = new ArrayList[pastureNum];
        for (int p = 0; p < pastureNum; p++) {
            neighbors[p] = new ArrayList<>();
        }
        for (int p = 0; p < pathNum; p++) {
            int[] path = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            path[0]--;  // make the pastures start from 0
            path[1]--;
            neighbors[path[0]].add(new int[] {path[1], path[2]});
            neighbors[path[1]].add(new int[] {path[0], path[2]});
        }

        int[] hay = new int[pastureNum];
        Arrays.fill(hay, -1);
        for (int h = 0; h < hayNum; h++) {
            int[] bale = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            bale[0]--;
            hay[bale[0]] = hay[bale[0]] == -1 ? bale[1] : Math.max(hay[bale[0]], bale[1]);
        }

        // minimum distances to the barn with and without eating some hay
        int[][] barnDist = new int[pastureNum][2];
        for (int p = 0; p < pastureNum; p++) {
            Arrays.fill(barnDist[p], Integer.MAX_VALUE);
        }
        barnDist[pastureNum - 1][0] = 0;
        // do a dijkstra's
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> p[0]));
        frontier.add(new int[] {0, pastureNum - 1, 0});
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            if (curr[0] != barnDist[curr[1]][curr[2]]) {
                continue;
            }
            curr = Arrays.copyOfRange(curr, 1, curr.length);
            int currCost = barnDist[curr[0]][curr[1]];
            for (int[] n : neighbors[curr[0]]) {
                // if we haven't eaten hay yet but there's one here now, maybe we can eat the hay here? depends
                if (curr[1] == 0 && hay[n[0]] != -1) {
                    int baleCost = currCost + n[1] - hay[n[0]];
                    if (baleCost < barnDist[n[0]][1]) {
                        barnDist[n[0]][1] = baleCost;
                        frontier.add(new int[] {baleCost, n[0], 1});
                    }
                }
                int newCost = currCost + n[1];
                if (newCost < barnDist[n[0]][curr[1]]) {
                    barnDist[n[0]][curr[1]] = newCost;
                    frontier.add(new int[] {newCost, n[0], curr[1]});
                }
            }
        }

        int[] eatable = new int[pastureNum - 1];
        for (int p = 0; p < pastureNum - 1; p++) {
            eatable[p] = barnDist[p][0] >= barnDist[p][1] ? 1 : 0;
        }
        PrintWriter written = new PrintWriter("dining.out");
        Arrays.stream(eatable).forEach(written::println);
        written.close();
        System.out.println(Arrays.toString(eatable));
        System.out.printf("bruh why is HAY considered fine dining: %d ms%n", System.currentTimeMillis() - start);
    }
}
