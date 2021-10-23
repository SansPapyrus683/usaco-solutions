package official.o2015.feb.gold.hopscotch;

import java.io.*;
import java.util.*;

// 2015 feb gold (aka the better solution to the silver version)
public final class Hopscotch {
    private static final int MOD = (int) 1e9 + 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("hopscotch.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rowNum = Integer.parseInt(initial.nextToken());
        int colNum = Integer.parseInt(initial.nextToken());
        int colorNum = Integer.parseInt(initial.nextToken());
        int[][] grid = new int[rowNum][colNum];
        boolean[] presentColors = new boolean[colorNum];
        for (int r = 0; r < rowNum; r++) {
            grid[r] = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
            for (int c : grid[r]) {
                presentColors[c] = true;
            }
        }

        long[][] numWays = new long[rowNum][colNum];
        long[][] prefWays = new long[rowNum][colNum];
        numWays[0][0] = 1;
        // we aren't gonna be processing the first row & column  so lemme just do it here
        for (int r = 0; r < rowNum; r++) {
            prefWays[r][0] = 1;
        }
        for (int c = 0; c < colNum; c++) {
            prefWays[0][c] = 1;
        }

        // compress the rows for which certain colors are present
        ArrayList<Integer>[] presentRows = new ArrayList[colorNum];
        for (int c = 0; c < colorNum; c++) {
            presentRows[c] = new ArrayList<>();
        }
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                int color = grid[r][c];
                if (presentRows[color].isEmpty() || r > presentRows[color].get(presentRows[color].size() - 1)) {
                    presentRows[color].add(r);
                }
            }
        }
        // System.out.println(Arrays.toString(presentRows));

        BITree[] colorWays = new BITree[colorNum];
        for (int c = 0; c < colorNum; c++) {
            if (presentColors[c]) {
                colorWays[c] = new BITree(presentRows[c].size());
            }
        }
        colorWays[grid[0][0]].increment(0, 1);
        for (int c = 1; c < colNum; c++) {
            for (int r = rowNum - 1; r >= 1; r--) {
                int color = grid[r][c];
                int comprRow = Collections.binarySearch(presentRows[color], r);
                long rawAmt = prefWays[r - 1][c - 1];
                long subtractAmt = colorWays[color].query(comprRow - 1);
                numWays[r][c] = rawAmt - subtractAmt + MOD;
                if (numWays[r][c] > MOD) {
                    numWays[r][c] -= MOD;
                }
                colorWays[grid[r][c]].increment(comprRow, numWays[r][c]);
            }
            for (int r = 1; r < rowNum; r++) {
                prefWays[r][c] = (
                        numWays[r][c]
                                + prefWays[r - 1][c]
                                + prefWays[r][c - 1]
                                - prefWays[r - 1][c - 1] + MOD
                ) % MOD;
            }
        }

        long totalWays = numWays[rowNum - 1][colNum - 1];
        PrintWriter written = new PrintWriter("hopscotch.out");
        written.println(totalWays);
        written.close();
        System.out.println(totalWays);
        System.out.printf("%d ms, probably not my best time lol%n", System.currentTimeMillis() - start);
    }

    private static class BITree {
        private final long[] treeThing;
        private final int size;
        public BITree(int size) {
            treeThing = new long[size + 1];
            this.size = size;
        }

        public void increment(int updateAt, long val) {
            updateAt++;  // have the driver code not worry about 1-indexing
            for (; updateAt <= size; updateAt += updateAt & -updateAt) {
                treeThing[updateAt] = (treeThing[updateAt] + val) % MOD;
            }
        }

        public long query(int ind) {  // the bound is inclusive i think
            ind++;
            long sum = 0;
            for (; ind > 0; ind -= ind & -ind) {
                sum = (sum + treeThing[ind]) % MOD;
            }
            return sum;
        }
    }
}
