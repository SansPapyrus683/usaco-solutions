package official.o2019.feb.gold.painbarn;

import java.io.*;
import java.util.*;
// i am so sorry
import static java.lang.Math.max;

// 2019 feb gold (i spent an entire day debugging the array indices on this oh my fricking god)
public class PaintBarn {
    static final int WIDTH = 200;
    
    static int[][] prefLeftovers;
    
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("paintbarn.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rectNum = Integer.parseInt(initial.nextToken());  // sounds like rectum ngl
        int optimalAmt = Integer.parseInt(initial.nextToken());
        int[][] barn = new int[WIDTH][WIDTH];
        for (int r = 0; r < rectNum; r++) {
            int[] rect = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            for (int y = rect[1]; y < rect[3]; y++) {
                barn[y][rect[0]]++;
                if (rect[2] < WIDTH) {
                    barn[y][rect[2]]--;
                }
            }
        }
        for (int r = 0; r < WIDTH; r++) {
            int soFar = 0;
            for (int c = 0; c < WIDTH; c++) {
                soFar += barn[r][c];
                barn[r][c] = soFar;
            }
        }

        /*
         * leftovers[r][c] = if we paint the cell there,
         * gives the amount of change in optimal paint size
         */
        int[][] leftovers = new int[WIDTH][WIDTH];
        int rnAmt = 0;
        for (int r = 0; r < WIDTH; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (barn[r][c] == optimalAmt) {
                    leftovers[r][c] = -1;
                    rnAmt++;
                } else if (barn[r][c] == optimalAmt - 1) {
                    leftovers[r][c] = 1;
                }
            }
        }

        // create a prefix sum array for easy 2d querying the leftovers array
        prefLeftovers = new int[WIDTH + 1][WIDTH + 1];
        for (int r = 1; r < WIDTH + 1; r++) {
            for (int c = 1; c < WIDTH + 1; c++) {
                prefLeftovers[r][c] = (
                    prefLeftovers[r - 1][c]
                    + prefLeftovers[r][c - 1]
                    - prefLeftovers[r - 1][c - 1]
                    + leftovers[r - 1][c - 1]
                );
            }
        }


        int[] topBest = new int[WIDTH];
        int[] bottomBest = new int[WIDTH];
        int[] leftBest = new int[WIDTH];
        int[] rightBest = new int[WIDTH];
        // iterate through all pairs of columns and rows for 2d kadane's
        for (int start = 0; start < WIDTH; start++) {
            for (int end = start; end < WIDTH; end++) {
                int topSum = 0;
                int leftSum = 0;
                int rect;
                for (int i = 1; i < WIDTH; i++) {
                    rect = topSum + rectSum(i - 1, start, i - 1, end);
                    topBest[i] = max(topBest[i], topSum = max(0, rect));

                    rect = leftSum + rectSum(start, i - 1, end, i - 1);
                    leftBest[i] = max(leftBest[i], leftSum = max(0, rect));
                }

                int bottomSum = 0;
                int rightSum = 0;
                for (int i = WIDTH - 1; i >= 1; i--) {
                    rect = bottomSum + rectSum(i, start, i, end);
                    bottomBest[i] = max(bottomBest[i], bottomSum = max(0, rect));

                    rect = rightSum + rectSum(start, i, end, i);
                    rightBest[i] = max(rightBest[i], rightSum = max(0, rect));
                }
            }
        }

        // run a cumulative maximum operation on these arrays
        for (int i = 1; i < WIDTH; i++) {
            topBest[i] = max(topBest[i], topBest[i - 1]);
            leftBest[i] = max(leftBest[i], leftBest[i - 1]);
        }
        for (int i = WIDTH - 2; i >= 0; i--) {
            bottomBest[i] = max(bottomBest[i], bottomBest[i + 1]);
            rightBest[i] = max(rightBest[i], rightBest[i + 1]);
        }

        // and finally run through all lines for the best combination
        int maxPaintable = 0;
        for (int i = 0; i < WIDTH; i++) {
            maxPaintable = max(maxPaintable, topBest[i] + bottomBest[i]);
            maxPaintable = max(maxPaintable, leftBest[i] + rightBest[i]);
        }

        PrintWriter written = new PrintWriter("paintbarn.out");
        written.println(rnAmt + maxPaintable);
        written.close();
        System.out.println(rnAmt + maxPaintable);
        System.out.printf("time: %d ms%n", System.currentTimeMillis() - timeStart);
    }

    // returns the sum of leftovers[from_r][from_c] to leftovers[to_r][to_c]
    static int rectSum(int fromR, int fromC, int toR, int toC) {
        return (
            prefLeftovers[toR + 1][toC + 1]
            - prefLeftovers[fromR][toC + 1]
            - prefLeftovers[toR + 1][fromC]
            + prefLeftovers[fromR][fromC]
        );
    }
}
