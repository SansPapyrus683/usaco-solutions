package official.o2015.feb.silver.jumpingTheScotch;

import java.util.*;
import java.io.*;
import java.util.stream.Stream;

// 2015 feb silver
public final class Hopscotch {
    private static final long MOD = 1000000007L;  // idk why they chose this number i mean
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("hopscotch.in"));
        String[] initial = read.readLine().split(" ");
        int[][] grid = new int[Integer.parseInt(initial[0])][Integer.parseInt(initial[1])];
        int[] end = new int[] {grid.length - 1, grid[0].length - 1};
        for (int i = 0; i < grid.length; i++) {
            grid[i] = Stream.of(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        Queue<int[]> frontier = new PriorityQueue<>((p1, p2) -> {
            if (p1[0] != p2[0]) {
                return p1[0] - p2[0];  // i mean just compare x coordinates (w/ y, it's the same thing)
            }
            return p1[1] - p2[1];
        });
        // this array stores how many ways we can go from the start to a given square (so when we bfs there aren't too many duplicates)
        long[][] multipliers = new long[grid.length][grid[0].length];
        frontier.add(new int[] {0, 0});
        multipliers[0][0] = 1; // 1 way from start to start kinda obvi
        long validWays = 1;
        while (!frontier.isEmpty()) {
            int[] current = frontier.poll();

            // calculate the places where we can jump to
            ArrayList<int[]> canGoTo = new ArrayList<>();
            int myColor = grid[current[0]][current[1]];
            for (int r = current[0] + 1; r < grid.length; r++) {  // -1 bc 0 based indexing
                for (int c = current[1] + 1; c < grid[0].length; c++) {
                    if (grid[r][c] != myColor &&
                            ((r < grid.length - 1 && c < grid[0].length - 1) ||  // leave out the borders (except for finish)
                             (r == grid.length - 1 && c == grid[0].length - 1))) {
                        canGoTo.add(new int[] {r, c});
                    }
                }
            }

            long numWays = multipliers[current[0]][current[1]];
            long increment = (numWays * (canGoTo.size() - 1)) % MOD;
            validWays = (validWays + increment) % MOD;
            validWays += validWays < 0 ? MOD : 0;

            for (int[] n : canGoTo) {
                if (Arrays.equals(n, end)) {  // nowhere to go from the end
                    continue;
                }

                if (multipliers[n[0]][n[1]] == 0) {  // =0 means that it's not explored
                    frontier.add(n);
                }
                multipliers[n[0]][n[1]] = (multipliers[n[0]][n[1]] + numWays) % MOD;
            }
        }

        PrintWriter written = new PrintWriter(new FileOutputStream("hopscotch.out"));
        written.println(validWays % MOD);  // just in case
        written.close();
        System.out.println(validWays % MOD);
        System.out.printf("ok took %d ms i am 120938741209384%% sure%n", System.currentTimeMillis() - start);
    }
}
