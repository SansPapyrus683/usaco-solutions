package official.o2014.usopen.silver.karenGPS;

import java.io.*;
import java.util.*;

// 2014 us open silver
public class GPSDuel {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("gpsduel.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int intNum = Integer.parseInt(initial.nextToken());  // intersection num,
        int roadNum = Integer.parseInt(initial.nextToken());

        ArrayList<int[]>[] neighbors = new ArrayList[intNum];
        ArrayList<int[]>[] reverseNeighbors = new ArrayList[intNum];
        for (int i = 0; i < intNum; i++) {
            neighbors[i] = new ArrayList<>();
            reverseNeighbors[i] = new ArrayList<>();
        }

        for (int r = 0; r < roadNum; r++) {
            StringTokenizer road = new StringTokenizer(read.readLine());
            int from = Integer.parseInt(road.nextToken()) - 1;
            int to = Integer.parseInt(road.nextToken()) - 1;
            int firstDist = Integer.parseInt(road.nextToken());
            int secondDist = Integer.parseInt(road.nextToken());
            neighbors[from].add(new int[] {to, firstDist, secondDist});
            reverseNeighbors[to].add(new int[] {from, firstDist, secondDist});
        }

        int[] firstDists = minDists(intNum - 1, reverseNeighbors, 1);
        int[] secondDists = minDists(intNum - 1, reverseNeighbors, 2);

        int[] minComplaints = new int[intNum];
        Arrays.fill(minComplaints, Integer.MAX_VALUE);
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> p[0]));
        frontier.add(new int[] {0, 0});
        minComplaints[0] = 0;
        while (!frontier.isEmpty()) {  // run a dijkstra's using the complaints as the costs
            int[] top = frontier.poll();
            int currComplaints = top[0];
            int curr = top[1];
            if (minComplaints[curr] != currComplaints) {
                continue;
            }
            if (curr == intNum - 1) {  // we've arrived at our destination!
                break;
            }
            for (int[] n : neighbors[curr]) {
                boolean firstShortest = n[1] + firstDists[n[0]] == firstDists[curr];
                boolean secondShortest = n[2] + secondDists[n[0]] == secondDists[curr];
                int nCost = currComplaints + (firstShortest ? 0 : 1) + (secondShortest ? 0 : 1);
                if (nCost < minComplaints[n[0]]) {
                    minComplaints[n[0]] = nCost;
                    frontier.add(new int[] {nCost, n[0]});
                }
            }
        }

        PrintWriter written = new PrintWriter("gpsduel.out");
        written.println(minComplaints[intNum - 1]);
        written.close();
        System.out.println(minComplaints[intNum - 1]);
        System.out.printf("my man you don't just casually get a time of %d ms%n", System.currentTimeMillis() - start);
    }

    private static int[] minDists(int start, List<int[]>[] neighbors, int costInd) {
        int[] minDist = new int[neighbors.length];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> p[0]));
        frontier.add(new int[] {0, start});
        minDist[start] = 0;
        while (!frontier.isEmpty()) {
            int[] top = frontier.poll();  // the current cost and the positition
            if (minDist[top[1]] != top[0]) {
                continue;
            }
            for (int[] n : neighbors[top[1]]) {
                int nCost = top[0] + n[costInd];
                if (nCost < minDist[n[0]]) {
                    minDist[n[0]] = nCost;
                    frontier.add(new int[] {nCost, n[0]});
                }
            }
        }
        return minDist;
    }
}
