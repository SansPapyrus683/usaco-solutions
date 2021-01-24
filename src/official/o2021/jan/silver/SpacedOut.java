package official.o2021.jan.silver;

import java.io.*;
import java.util.*;

/**
 * 2021 jan silver
 * 4
 * 3 3 1 1
 * 1 1 3 1
 * 3 3 1 1
 * 1 1 3 3 should output 22
 * so notice that to satisfy fj's ocd
 * all the rows (or all the columns) have to be inversions of each other
 * so we can just calculate the sums for each alternating row/col,
 * and then just greedy it out
 */
public class SpacedOut {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int width = Integer.parseInt(initial.nextToken());
        int[][] topDownSums = new int[2][width];
        int[][] leftRightSums = new int[2][width];
        for (int r = 0; r < width; r++) {
            int[] beauty = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int[] row = topDownSums[r % 2];
            for (int c = 0; c < width; c++) {
                row[c] += beauty[c];
                leftRightSums[c % 2][r] += beauty[c];
            }
        }

        long start = System.currentTimeMillis();  // this probably neglects a large portion of the time but whatever
        int beautifulest = Math.max(maxSum(topDownSums), maxSum(leftRightSums));
        System.out.println(beautifulest);
        System.err.printf("%d ms thank you come again%n", System.currentTimeMillis() - start);
    }

    private static int maxSum(int[][] alternating) {
        int total = 0;
        for (int c = 0; c < alternating[0].length; c++) {
            int colMax = 0;
            for (int r = 0; r < alternating.length; r++) {
                colMax = Math.max(colMax, alternating[r][c]);
            }
            total += colMax;
        }
        return total;
    }
}
