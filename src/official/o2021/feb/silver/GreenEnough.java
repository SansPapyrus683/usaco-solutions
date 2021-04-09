package official.o2021.feb.silver;

import java.io.*;
import java.util.*;

/**
 * 2021 feb silver
 * 3
 * 57 120 87
 * 200 100 150
 * 2 141 135 should output 8
 */
public final class GreenEnough {
    private static final int MIN = 100;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int sideLen = Integer.parseInt(read.readLine());
        int[][] field = new int[sideLen][sideLen];
        for (int r = 0; r < sideLen; r++) {
            field[r] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        // do some complementary counting to get the number of fields that have a min of exactly 100
        System.out.println(minMinMat(field, MIN) - minMinMat(field, MIN + 1));
        System.err.printf("this code that took %d ms is absolutely pointless lol%n", System.currentTimeMillis() - start);
    }

    // the number of submatrices with a minimum that can only be so small as minMin (the minimum minimum)
    private static long minMinMat(int[][] mat, int minMin) {
        boolean[][] valid = new boolean[mat.length][mat[0].length];
        for (int r = 0; r < mat.length; r++) {
            for (int c = 0; c < mat[0].length; c++) {
                valid[r][c] = mat[r][c] >= minMin;
            }
        }
        return allTrueMat(valid);
    }

    // sauce: https://www.geeksforgeeks.org/number-of-submatrices-with-all-1s/
    private static long allTrueMat(boolean[][] arr) {
        int[][] streakArr = rightBoolStreaks(arr);
        long allTrueNum = 0;
        for (int c = 0; c < arr[0].length; c++) {
            ArrayDeque<int[]> relevantStreaks = new ArrayDeque<>();
            int currSum = 0;  // the current amt of valid submatrices
            // for each row in reverse, count # of valid submatrices that start at that cell
            for (int r = arr.length - 1; r >= 0; r--) {
                int popped = 0;
                while (!relevantStreaks.isEmpty() && relevantStreaks.peek()[0] > streakArr[r][c]) {
                    currSum -= (relevantStreaks.peek()[1] + 1) * (relevantStreaks.peek()[0] - streakArr[r][c]);
                    popped += relevantStreaks.peek()[1] + 1;
                    relevantStreaks.pop();
                }
                relevantStreaks.addFirst(new int[] {streakArr[r][c], popped});
                currSum += streakArr[r][c];  // add the submatrices of height 1
                allTrueNum += currSum;
            }
        }
        return allTrueNum;
    }

    private static int[][] rightBoolStreaks(boolean[][] arr) {
        // each of these elements contains the amount of consecutive trues to its right (including itself)
        int[][] streakArr = new int[arr.length][arr[0].length];
        for (int r = 0; r < arr.length; r++) {
            for (int c = arr[0].length - 1; c >= 0; c--) {
                if (!arr[r][c]) {
                    continue;
                }
                if (c != arr.length - 1) {
                    streakArr[r][c] += streakArr[r][c + 1];
                }
                streakArr[r][c] += arr[r][c] ? 1 : 0;
            }
        }
        return streakArr;
    }
}
