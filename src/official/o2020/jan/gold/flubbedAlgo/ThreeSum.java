package official.o2020.jan.gold.flubbedAlgo;

import java.io.*;
import java.util.*;

// 2020 jan gold
public class ThreeSum {
    private static final int TARGET = 0;
    private static final int MAX = (int) Math.pow(10, 6);
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("threesum.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int size = Integer.parseInt(initial.nextToken());
        int queryNum = Integer.parseInt(initial.nextToken());
        int[] numbers = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        long[][] rangeValid = new long[size][size];
        /*
         * this for loop first fills the array with i...j
         * with how many numbers between i...j sum with numbers[i] and numbers[j]
         * to fill the target (which in this case is 0)
         */
        int[] leftovers = new int[2 * MAX + 1];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int supposed = TARGET - numbers[i] - numbers[j] + MAX;
                if (0 <= supposed && supposed <= 2 * MAX) {
                    rangeValid[i][j] = leftovers[supposed];
                }
                leftovers[numbers[j] + MAX]++;
            }
            // revert the changes and stuff
            for (int j = i + 1; j < size; j++) {
                leftovers[numbers[j] + MAX]--;
            }
        }

        /*
         * this fills out the rest of the array
         * it's almost prefix-sum esque, like first take 1 to the left,
         * then take one to the right, then take out the duplicate middle
         */
        for (int i = size - 1; i >= 0; i--) {
            for (int j = i + 1; j < size; j++) {
                rangeValid[i][j] += rangeValid[i + 1][j] + rangeValid[i][j - 1] - rangeValid[i + 1][j - 1];
            }
        }

        PrintWriter written = new PrintWriter("threesum.out");
        for (int q = 0; q < queryNum; q++) {
            StringTokenizer query = new StringTokenizer(read.readLine());
            written.println(
                    rangeValid[Integer.parseInt(query.nextToken()) - 1][Integer.parseInt(query.nextToken()) - 1]
            );
        }
        written.close();
        System.out.printf("i'm not mad, i'm just disappointed that you code took %d ms%n", System.currentTimeMillis() - start);
    }
}

