package official.o2017.feb.gold.cookedCows;

import java.io.*;
import java.util.*;

// 2017 feb gold
public final class VisitFJ {
    // we count bessie's moves as "3 jumps", and that's why these arrays are so huge
    private static final int[] CHANGE_R = new int[] {0, 0, 0, 0, 1, 1, 1, -1, -1, -1, 2, 2, -2, -2, 3, 3, -3, -3};
    private static final int[] CHANGE_C = new int[] {-3, 3, 1, -1, -2, 0, 2, -2, 0, 2, -1, 1, -1, 1,  0, 0, 0, 0};
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("visitfj.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int side = Integer.parseInt(initial.nextToken());
        int roadTime = Integer.parseInt(initial.nextToken());
        int[][] fields = new int[side][side];
        for (int i = 0; i < side; i++) {
            fields[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int[][] distances = new int[side][side];  // dijkstra's with those 3 jumps
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> p[0]));
        for (int i = 0; i < side; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }
        distances[0][0] = 0;
        frontier.add(new int[] {0, 0, 0});
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            if (distances[curr[1]][curr[2]] != curr[0]) {
                continue;
            }
            int rnCost = distances[curr[1]][curr[2]];  // 1 and 2 because ind 0 contains the cost
            int distToEnd;

            if ((distToEnd = Math.abs((side - 1) - curr[1]) + Math.abs((side - 1) - curr[2])) < 3) {
                distances[side - 1][side - 1] = Math.min(distances[side - 1][side - 1], rnCost + distToEnd * roadTime);
                continue;
            }
            for (int[] n : squareNeighbors(curr[1], curr[2], side)) {
                int nCost = rnCost + 3 * roadTime + fields[n[0]][n[1]];
                if (distances[n[0]][n[1]] > nCost) {
                    distances[n[0]][n[1]] = nCost;
                    frontier.add(new int[] {nCost, n[0], n[1]});
                }
            }
        }

        PrintWriter written = new PrintWriter("visitfj.out");
        written.println(distances[side - 1][side - 1]);
        written.close();
        System.out.println(distances[side - 1][side - 1]);
        System.out.printf("it finished in a jiffy, if by jiffy you mean %d ms%n", System.currentTimeMillis() - start);
    }

    private static ArrayList<int[]> squareNeighbors(int r, int c, int side) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        for (int i = 0; i < CHANGE_R.length; i++) {
            int newR = r + CHANGE_R[i];
            int newC = c + CHANGE_C[i];
            if (0 <= newR && newR < side && 0 <= newC && newC < side) {  // it just has to be in the bounds
                neighbors.add(new int[] {newR, newC});
            }
        }
        return neighbors;
    }
}
