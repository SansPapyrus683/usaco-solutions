import java.io.*;
import java.util.*;

// 2017 feb gold
public class VisitFJ {
    // we count bessie's moves as "3 jumps", and that's why these arrays are so huge
    private static final int[] CHANGE_R = new int[] {0, 0, 0,  0,  1,  1, 1, -1, -1, -1, 2,  2, -2, -2, 3, 3, -3, -3};
    private static final int[] CHANGE_C = new int[] {-3, 3, 1, -1, -2, 0, 2, -2, 0,  2,  -1, 1, -1, 1,  0, 0, 0,  0};
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
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> distances[p[0]][p[1]]));
        for (int i = 0; i < side; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }
        distances[0][0] = 0;
        frontier.add(new int[] {0, 0});
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            int rnCost = distances[curr[0]][curr[1]];
            int distToEnd;
            // ok, we can go to the end, but just MAYBE there's going to be a shorter path, so just store it and move on
            if ((distToEnd = Math.abs((side - 1) - curr[0]) + Math.abs((side - 1) - curr[1])) < 3) {
                distances[side - 1][side - 1] = Math.min(distances[side - 1][side - 1], rnCost + distToEnd * roadTime);
                continue;
            }
            for (int[] n : squareNeighbors(curr[0], curr[1], side)) {
                int newCost = rnCost + 3 * roadTime + fields[n[0]][n[1]];
                if (distances[n[0]][n[1]] > newCost) {
                    distances[n[0]][n[1]] = newCost;
                    frontier.add(n);
                }
            }
        }

        PrintWriter written = new PrintWriter("visitfj.out");
        written.println(distances[side - 1][side - 1]);
        written.close();
        System.out.println(distances[side - 1][side - 1]);
        System.out.printf("it finished in a jiffy, if by jiffy you mean %d ms%n", System.currentTimeMillis() - start);
    }

    static ArrayList<int[]> squareNeighbors(int r, int c, int side) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            int newR = r + CHANGE_R[i];
            int newC = c + CHANGE_C[i];
            if (0 <= newR && newR < side && 0 <= newC && newC < side) {  // it just has to be in the bounds
                neighbors.add(new int[] {newR, newC});
            }
        }
        return neighbors;
    }
}
