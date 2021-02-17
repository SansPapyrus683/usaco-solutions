package official.o2017.usopen.silver.lostBessie;

import java.io.*;
import java.util.*;

// 2017 usopen silver
public final class Where {
    static final int[] CHANGE_R = new int[] {1, -1, 0, 0};
    static final int[] CHANGE_C = new int[] {0, 0, 1, -1};
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("where.in"));
        int side = Integer.parseInt(read.readLine());
        int[][] field = new int[side][side];
        for (int i = 0; i < side; i++) {
            field[i] = read.readLine().chars().map(a -> a - 'A').toArray();
        }

        // brute force all possible corners
        ArrayList<int[][]> regions = new ArrayList<>();
        for (int r1 = 0; r1 < side; r1++) {
            for (int c1 = 0; c1 < side; c1++) {
                for (int r2 = r1; r2 < side; r2++) {
                    for (int c2 = c1; c2 < side; c2++) {
                        if (validCorners(field, new int[] {r1, c1}, new int[] {r2, c2})) {
                            regions.add(new int[][] {{r1, c1}, {r2, c2}});
                        }
                    }
                }
            }
        }

        // ok now check for all the regions that are subregions of each other
        int ans = regions.size();
        for (int[][] r : regions) {
            for (int[][] checkAgainst : regions) {
                if (!Arrays.deepEquals(r, checkAgainst) &&
                        subregion(checkAgainst[0], checkAgainst[1], r[0], r[1])) {
                    ans--;
                    break;
                }
            }
        }

        PrintWriter written = new PrintWriter("where.out");
        written.println(ans);
        written.close();
        System.out.println(ans);
        System.out.printf("%d ms, you can all leave now, have a good day%n", System.currentTimeMillis() - start);
    }

    // checks whether 2 is within 1 (boundaries count as being within)
    static boolean subregion(int[] topLeft1, int[] bottomRight1, int[] topLeft2, int[] bottomRight2) {
        return topLeft1[0] <= topLeft2[0] && bottomRight2[0] <= bottomRight1[0] &&
                topLeft1[1] <= topLeft2[1] && bottomRight2[1] <= bottomRight1[1];
    }

    // the coordinates are from the top right but the corners are described using "normal" terms
    static boolean validCorners(int[][] field, int[] topLeft, int[] bottomRight) {
        if (topLeft[0] > bottomRight[0] || topLeft[1] > bottomRight[1]) {
            throw new IllegalArgumentException("the corners are wrong buddy");
        }

        HashMap<Integer, Integer> regionCounts = new HashMap<>();
        boolean[][] visited = new boolean[field.length][field[0].length];
        for (int sr = topLeft[0]; sr <= bottomRight[0]; sr++) {  // start row and start column
            for (int sc = topLeft[1]; sc <= bottomRight[1]; sc++) {
                if (visited[sr][sc]) {
                    continue;
                }
                visited[sr][sc] = true;
                int target = field[sr][sc];
                ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {sr, sc}));
                regionCounts.put(target, regionCounts.getOrDefault(target, 0) + 1);
                while (!frontier.isEmpty()) {
                    int[] curr = frontier.poll();
                    for (int i = 0; i < 4; i++) {
                        int r = curr[0] + CHANGE_R[i];
                        int c = curr[1] + CHANGE_C[i];
                        // check if it's in the bounds, hasn't been visited before, and is consistent w/ the regions
                        if (topLeft[0] <= r && r <= bottomRight[0] && topLeft[1] <= c && c <= bottomRight[1]
                                && !visited[r][c] && field[r][c] == target) {
                            visited[r][c] = true;
                            frontier.add(new int[] {r, c});
                        }
                    }
                }
            }
        }
        return regionCounts.size() == 2 && regionCounts.containsValue(1) && regionCounts.values().stream().anyMatch(c -> c >= 2);
    }
}
