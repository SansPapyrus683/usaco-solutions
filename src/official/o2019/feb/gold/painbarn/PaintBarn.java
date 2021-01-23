package official.o2019.feb.gold.painbarn;

import java.io.*;
import java.util.*;
// i am so sorry
import static java.lang.Math.max;

// 2019 feb gold (i spent an entire day debugging the array indices on this oh my fricking god)
public class PaintBarn {
    private static final int BARN_WIDTH = 200;
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("paintbarn.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rectNum = Integer.parseInt(initial.nextToken());  // sounds like rectum ngl
        int optimalAmt = Integer.parseInt(initial.nextToken());
        int[][] barn = new int[BARN_WIDTH][BARN_WIDTH];
        for (int r = 0; r < rectNum; r++) {
            int[] rect = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int i = rect[1]; i < rect[3]; i++) {
                barn[i][rect[0]]++;
                if (rect[2] < BARN_WIDTH) {
                    barn[i][rect[2]]--;
                }
            }
        }
        for (int r = 0; r < BARN_WIDTH; r++) {
            int soFar = 0;
            for (int c = 0; c < BARN_WIDTH; c++) {
                soFar += barn[r][c];
                barn[r][c] = soFar;
            }
        }

        int[][] leftovers = new int[BARN_WIDTH][BARN_WIDTH];
        int rnAmt = 0;
        for (int r = 0; r < BARN_WIDTH; r++) {
            for (int c = 0; c < BARN_WIDTH; c++) {
                if (barn[r][c] == optimalAmt) {
                    leftovers[r][c] = -1;
                    rnAmt++;
                } else if (barn[r][c] == optimalAmt - 1) {
                    leftovers[r][c] = 1;
                }
            }
        }

        int[][] prefixedLeftovers = new int[BARN_WIDTH + 1][BARN_WIDTH + 1];
        for (int r = 1; r < BARN_WIDTH + 1; r++) {  // make a prefix array of the leftovers for O(1) range sums and stuff
            for (int c = 1; c < BARN_WIDTH + 1; c++) {
                prefixedLeftovers[r][c] = prefixedLeftovers[r - 1][c]
                        + prefixedLeftovers[r][c - 1]
                        - prefixedLeftovers[r - 1][c - 1]
                        + leftovers[r - 1][c - 1];  // this time r - 1 because we started at 1 remember
            }
        }

        // these *should* be the best amt we can get by painting a rectangle adjacent to a line drawn there
        int[] topBest = new int[BARN_WIDTH];  // like this should be the best rectangle above y=i where i is the index
        int[] bottomBest = new int[BARN_WIDTH];
        int[] leftBest = new int[BARN_WIDTH];
        int[] rightBest = new int[BARN_WIDTH];

        // this part is just copied right from the sol lol
        for (int bound = 0; bound < BARN_WIDTH + 1; bound++) {
            for (int extendBy = 0; bound + extendBy < BARN_WIDTH; extendBy++) {
                int topSum = 0;
                int bottomSum = 0;
                int leftSum = 0;
                int rightSum = 0;
                for (int i = 1; i < BARN_WIDTH; i++) {  // think this is called kadane's algo or something
                    topBest[i] = max(topBest[i],
                            topSum = max(0, topSum + rectSum(prefixedLeftovers, i - 1, bound, i - 1, bound + extendBy)));

                    bottomBest[BARN_WIDTH - i] = max(bottomBest[BARN_WIDTH - i],
                            bottomSum = max(0, bottomSum + rectSum(prefixedLeftovers, BARN_WIDTH - i, bound, BARN_WIDTH - i, bound + extendBy)));

                    leftBest[i] = max(leftBest[i],
                            leftSum = max(0, leftSum + rectSum(prefixedLeftovers, bound, i - 1, bound + extendBy, i - 1)));

                    rightBest[BARN_WIDTH - i] = max(rightBest[BARN_WIDTH - i],
                            rightSum = max(0, rightSum + rectSum(prefixedLeftovers, bound, BARN_WIDTH - i, bound + extendBy, BARN_WIDTH - i)));
                }
            }
        }
        // "carry" all the best ones over
        for (int i = 1; i < BARN_WIDTH; i++) {
            topBest[i] = max(topBest[i], topBest[i - 1]);
            bottomBest[BARN_WIDTH - i - 1] = max(bottomBest[BARN_WIDTH - i - 1], bottomBest[BARN_WIDTH - i]);
            leftBest[i] = max(leftBest[i], leftBest[i - 1]);
            rightBest[BARN_WIDTH - i - 1] = max(rightBest[BARN_WIDTH - i - 1], rightBest[BARN_WIDTH - i]);
        }
        int maxPaintable = 0;
        for (int i = 0; i < BARN_WIDTH; i++) {
            maxPaintable = max(maxPaintable, topBest[i] + bottomBest[i]);
            maxPaintable = max(maxPaintable, leftBest[i] + rightBest[i]);
        }

        PrintWriter written = new PrintWriter("paintbarn.out");
        written.println(rnAmt + maxPaintable);
        written.close();
        System.out.println(rnAmt + maxPaintable);
        System.out.printf("i loathe farmer john with the passion of a million fierce suns: %d ms", System.currentTimeMillis() - timeStart);
    }

    // so this takes the prefixArr (note that it's like "shifted" 1 down and 1 right)
    // the coordinates are relative to the original array, not the prefix array and are inclusive
    private static int rectSum(int[][] prefixArr, int fromR, int fromC, int toR, int toC) {
        return prefixArr[toR + 1][toC + 1] - prefixArr[fromR][toC + 1] - prefixArr[toR + 1][fromC] + prefixArr[fromR][fromC];
    }
}
