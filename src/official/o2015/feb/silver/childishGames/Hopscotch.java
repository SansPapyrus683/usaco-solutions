package official.o2015.feb.silver.childishGames;

import java.io.*;
import java.util.*;

// 2015 feb silver
public final class Hopscotch {
    private static final long MOD = (long) 1e9 + 7;  // idk why they chose this number i mean
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("hopscotch.in"));
        String[] initial = read.readLine().split(" ");
        int rowNum = Integer.parseInt(initial[0]);
        int colNum = Integer.parseInt(initial[1]);
        int[][] grid = new int[rowNum][colNum];
        int[] end = new int[] {grid.length - 1, grid[0].length - 1};
        for (int i = 0; i < grid.length; i++) {
            grid[i] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        long[][] numWays = new long[rowNum][colNum];
        numWays[0][0] = 1;
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                // go through all previous squares and add their methods to the current square
                for (int prevR = 0; prevR < r; prevR++) {
                    for (int prevC = 0; prevC < c; prevC++) {
                        if (grid[r][c] != grid[prevR][prevC]) {
                            numWays[r][c] = (numWays[r][c] + numWays[prevR][prevC]) % MOD;
                        }
                    }
                }
            }
        }

        long totalWays = numWays[rowNum - 1][colNum - 1];
        PrintWriter written = new PrintWriter(new FileOutputStream("hopscotch.out"));
        written.println(totalWays);
        written.close();
        System.out.println(totalWays);
        System.out.printf("ok took %d ms i am 120938741209384%% sure%n", System.currentTimeMillis() - start);
    }
}
