import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2015 jan silver (fails for 6 oof)
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
        long[][] moveCosts = new long[1000][planeNum + 1];  // min amount of city jumps it takes
        for (int i = 0; i < 1000; i++) {
            Arrays.fill(travelCosts[i], Long.MAX_VALUE);
            Arrays.fill(moveCosts[i], Long.MAX_VALUE);
        }
        PriorityQueue<long[]> frontier = new PriorityQueue<>((a, b) -> {
            if (travelCosts[(int) a[0]][(int) a[1]] != travelCosts[(int) b[0]][(int) b[1]]) {
                return travelCosts[(int) a[0]][(int) a[1]] - travelCosts[(int) b[0]][(int) b[1]] < 0 ? -1 : 1;
            }
            return (int) (moveCosts[(int) a[0]][(int) a[1]] - moveCosts[(int) b[0]][(int) b[1]]);
        });
        frontier.add(new long[] {(long) start, planeNum});  // city num and plane id
        travelCosts[start][planeNum] = 0;  // there actually is no route planeNum (bc 0 indexing)
        moveCosts[start][planeNum] = 0;  // im using it to just indicate no plane has been got on yet

        long minCost = Long.MAX_VALUE;
        long minCostMovement = Long.MAX_VALUE;
        while (!frontier.isEmpty()) {
            long[] got = frontier.poll();
            int[] curr = new int[2];
            for (int i = 0; i < 2; i++) {
                curr[i] = (int) got[i];
            }
            if (curr[0] == end) {
                minCost = travelCosts[curr[0]][curr[1]];
                minCostMovement = moveCosts[curr[0]][curr[1]];
                break;
            }

            long rnCost = travelCosts[curr[0]][curr[1]];
            long rnMoveCost = moveCosts[curr[0]][curr[1]] + 1;
            for (int[] n : canGoTo[curr[0]]) {
                long cost = rnCost;
                cost += curr[1] != n[1] ? n[2] : 0;
                if (cost < travelCosts[n[0]][n[1]] ||
                        (travelCosts[n[0]][n[1]] == cost && rnMoveCost < moveCosts[n[0]][n[1]])) {
                    travelCosts[n[0]][n[1]] = cost;
                    moveCosts[n[0]][n[1]] = rnMoveCost;
                    frontier.add(new long[] {(long) n[0], (long) n[1]});
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
