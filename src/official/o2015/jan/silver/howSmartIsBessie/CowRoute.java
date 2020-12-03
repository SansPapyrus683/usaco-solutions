package official.o2015.jan.silver.howSmartIsBessie;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2015 jan silver
public class CowRoute {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader(new File("cowroute.in")));
        String[] initialInfo = read.readLine().split(" ");
        int start = Integer.parseInt(initialInfo[0]) - 1, end = Integer.parseInt(initialInfo[1]) - 1;
        int planeNum = Integer.parseInt(initialInfo[2]);
        ArrayList<int[]>[] canGoTo = new ArrayList[1000];  // can go to pos, plane id, cost
        for (int i = 0; i < 1000; i++) {
            canGoTo[i] = new ArrayList<>();
        }

        for (int p = 0; p < planeNum; p++) {
            int cost = Integer.parseInt(read.readLine().split(" ")[0]);
            int[] path = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int i = 0; i < path.length; i++) {
                path[i]--;  // SCREW YOU 1-BASED INDEXING
            }
            for (int i = 0; i < path.length; i++) {
                if (i + 1 < path.length) {  // if statement because of the end
                    canGoTo[path[i]].add(new int[] {path[i + 1], p, cost});  // the city, plane id, and cost
                }
            }
        }
        long[][] travelCosts = new long[1000][planeNum + 1];  // the actual money it takes
        int[][] moveCosts = new int[1000][planeNum + 1];  // min amount of city jumps it takes
        for (int i = 0; i < 1000; i++) {
            Arrays.fill(travelCosts[i], Long.MAX_VALUE);
            Arrays.fill(moveCosts[i], Integer.MAX_VALUE);
        }
        PriorityQueue<int[]> frontier = new PriorityQueue<>((a, b) -> {
            if (travelCosts[a[0]][a[1]] != travelCosts[b[0]][b[1]]) {
                return travelCosts[a[0]][a[1]] - travelCosts[b[0]][b[1]] < 0 ? -1 : 1;
            }
            return moveCosts[a[0]][a[1]] - moveCosts[b[0]][b[1]];
        });
        frontier.add(new int[] {start, planeNum});  // city num and plane id
        travelCosts[start][planeNum] = 0;  // there actually is no route planeNum (bc 0 indexing)
        moveCosts[start][planeNum] = 0;  // im using it to just indicate no plane has been got on yet

        long minCost = Long.MAX_VALUE;
        int minCostMovement = Integer.MAX_VALUE;
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            long rnCost = travelCosts[curr[0]][curr[1]];
            int rnMoveCost = moveCosts[curr[0]][curr[1]] + 1;

            if (curr[0] == end) {
                if (rnCost < minCost) {
                    minCost = rnCost;
                    minCostMovement = rnMoveCost - 1;  // -1 because we're already at the end, why that +1 above lol
                } else if (rnCost == minCost) {
                    minCostMovement = Math.min(minCostMovement, rnMoveCost - 1);
                }
                continue;  // sometimes the algo glitches out, so don't break- just continue
            }
            for (int[] n : canGoTo[curr[0]]) {
                long cost = rnCost;
                cost += curr[1] != n[1] ? n[2] : 0;  // if we're continuing on that plane, don't add the cost
                if (cost < travelCosts[n[0]][n[1]] ||
                        (travelCosts[n[0]][n[1]] == cost && rnMoveCost < moveCosts[n[0]][n[1]])) {
                    travelCosts[n[0]][n[1]] = cost;
                    moveCosts[n[0]][n[1]] = rnMoveCost;
                    frontier.add(new int[] {n[0], n[1]});
                }
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("cowroute.out"));
        if (minCost != Long.MAX_VALUE) {
            written.printf("%s %s%n", minCost, minCostMovement);
            System.out.printf("%s %s%n", minCost, minCostMovement);
        } else {
            written.println("-1 -1");
            System.out.println("-1 -1");
        }
        written.close();
        System.out.printf("why are we still here- just to time code? (%s ms)", System.currentTimeMillis() - timeStart);
    }
}
