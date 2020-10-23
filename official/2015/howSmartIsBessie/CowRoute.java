import java.io.*;
import java.util.*;
import java.util.stream.Stream;

// 2015 jan silver (fails for 7 oof)
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
                    canGoTo[path[i]].add(new int[] {path[i + 1], p, cost});
                }
            }
        }

        long[][] travelCosts = new long[1000][planeNum + 1];  // the actual money it takes
        long[][] moveCosts = new long[1000][planeNum + 1];  // min amount of city jumps it takes
        for (int i = 0; i < 1000; i++) {
            Arrays.fill(travelCosts[i], Long.MAX_VALUE);
            Arrays.fill(moveCosts[i], Long.MAX_VALUE);
        }
        Queue<long[]> frontier = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {  // first compare the actual costs
                return (int) (a[0] - b[0]);
            } else {  // if 0, then compare the steps taken to get to the costs
                return (int) (a[3] - b[3]);
            }
        });
        frontier.add(new long[] {0L, (long) start, planeNum, 0L});  // priority, pos, plane id, moves taken
        travelCosts[start][planeNum] = 0;  // there actually is no route planeNum (bc 0 indexing)
        moveCosts[start][planeNum] = 0;  // im using it to just indicate no plane has been got on yet

        long minCost = Long.MAX_VALUE;
        long minCostMovement = Long.MAX_VALUE;
        while (!frontier.isEmpty()) {
            long[] got = Arrays.copyOfRange(frontier.poll(), 1, 4);
            int[] curr = new int[3];
            for (int i = 0; i < 3; i++) {
                curr[i] = (int) got[i];
            }
            if (curr[0] == end) {
                minCost = travelCosts[curr[0]][curr[1]];
                minCostMovement = moveCosts[curr[0]][curr[1]];
                break;
            }
            for (int[] n : canGoTo[curr[0]]) {
                long cost = curr[1] == n[1] ? travelCosts[curr[0]][curr[1]] :
                                              travelCosts[curr[0]][curr[1]] + n[2];
                if (cost < travelCosts[n[0]][n[1]] ||
                        (travelCosts[n[0]][n[1]] == cost && curr[2] + 1 < moveCosts[n[0]][n[1]])) {
                    System.out.printf("going from %s to %s, which costs %s          (it initially cost %s & %s moves)%n",
                            Arrays.toString(curr),
                            Arrays.toString(new long[] {(long) n[0], (long) n[1], curr[2] + 1}),
                            cost, travelCosts[n[0]][n[1]], moveCosts[n[0]][n[1]]);
                    travelCosts[n[0]][n[1]] = cost;
                    moveCosts[n[0]][n[1]] = curr[2] + 1;
                    frontier.add(new long[] {cost, (long) n[0], (long) n[1], curr[2] + 1});
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
