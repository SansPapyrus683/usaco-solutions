package official.o2019.dec.gold.capitalistFJ;

import java.io.*;
import java.util.*;

// 2019 dec gold
public class Pump {
    private static final int INVALID = 420696969;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("pump.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int junctionNum = Integer.parseInt(initial.nextToken());
        int pipeNum = Integer.parseInt(initial.nextToken());
        Set<Integer> flowRates = new HashSet<>();
        ArrayList<int[]>[] neighbors = new ArrayList[junctionNum];
        for (int j = 0; j < junctionNum; j++) {
            neighbors[j] = new ArrayList<>();
        }
        for (int p = 0; p < pipeNum; p++) {
            int[] pipe = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            pipe[0]--;  // make it 0 indexed
            pipe[1]--;
            neighbors[pipe[0]].add(new int[] {pipe[1], pipe[2], pipe[3]});
            neighbors[pipe[1]].add(new int[] {pipe[0], pipe[2], pipe[3]});
            flowRates.add(pipe[3]);
        }

        float maxScore = 0;
        for (int r : flowRates) {
            // for each possible flow rate, we calculate the minimum cost with that flow rate
            maxScore = Math.max(maxScore, ((float) r) / minCostWithFlow(r, neighbors));
        }

        PrintWriter written = new PrintWriter("pump.out");
        written.println((int) (maxScore * Math.pow(10, 6)));
        written.close();
        System.out.println(maxScore);
        System.out.printf("%d ms bruh you're so bad git gud%n", System.currentTimeMillis() - start);
    }

    private static int minCostWithFlow(int flowReq, ArrayList<int[]>[] neighbors) {
        // run dijkstra's twice to find the shortest dist from start & dist from end
        int[] forwardCosts = new int[neighbors.length];
        Arrays.fill(forwardCosts, INVALID);  // not MAX_VALUE because overflow is a possibility
        forwardCosts[0] = 0;
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(j -> j[0]));
        frontier.add(new int[] {0, 0});
        while (!frontier.isEmpty()) {
            int[] top = frontier.poll();
            if (top[0] != forwardCosts[top[1]]) {
                continue;
            }
            int curr = top[1];
            int rnCost = forwardCosts[curr];
            for (int[] n : neighbors[curr]) {
                if (rnCost + n[1] < forwardCosts[n[0]] && n[2] >= flowReq) {
                    forwardCosts[n[0]] = rnCost + n[1];
                    frontier.add(new int[] {rnCost + n[1], n[0]});
                }
            }
        }

        int[] backwardCosts = new int[neighbors.length];
        Arrays.fill(backwardCosts, INVALID);
        backwardCosts[neighbors.length - 1] = 0;
        // dw the frontier will always be empty by this point
        frontier.add(new int[] {0, neighbors.length - 1});
        while (!frontier.isEmpty()) {
            int[] top = frontier.poll();
            if (top[0] != backwardCosts[top[1]]) {
                continue;
            }
            int curr = top[1];
            int rnCost = backwardCosts[curr];
            for (int[] n : neighbors[curr]) {
                if (rnCost + n[1] < backwardCosts[n[0]] && n[2] >= flowReq) {
                    backwardCosts[n[0]] = rnCost + n[1];
                    frontier.add(new int[] {rnCost + n[1], n[0]});
                }
            }
        }

        // for each of the pipes that == the flow req we update the min cost
        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < neighbors.length; j++) {
            for (int[] n : neighbors[j]) {
                if (n[2] == flowReq) {
                    // we first go to junction j, then traverse the pipe, then to the end from the other junc
                    minCost = Math.min(minCost, forwardCosts[j] + n[1] + backwardCosts[n[0]]);
                }
            }
        }
        return minCost;
    }
}
