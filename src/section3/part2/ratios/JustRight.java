package section3.part2.ratios;
/*
ID: kevinsh4
TASK: ratios
LANG: JAVA
*/
import java.io.*;
import java.util.Arrays;

public class JustRight {
    private static final int FEED_NUM = 3;
    private static final int NUTRIENT_NUM = 3;
    private static final int MAX_RATIO = 100;
    // 4/19/2021- i don't know what made me think global variables were a good idea
    private static int[] goal = new int[FEED_NUM];
    private static int[][] mixes = new int[FEED_NUM][NUTRIENT_NUM];
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("ratios.in"));
        goal = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        mixes = new int[FEED_NUM][NUTRIENT_NUM];
        for (int f = 0; f < FEED_NUM; f++) {
            mixes[f] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int[] answer = null;
        bruteForce:
        for (int f1 = 0; f1 < MAX_RATIO; f1++) {
            for (int f2 = 0; f2 < MAX_RATIO; f2++) {
                for (int f3 = 0; f3 < MAX_RATIO; f3++) {
                    if (Arrays.equals(new int[] {f1, f2, f3}, new int[] {0, 0, 0})) {
                        continue;
                    }
                    if (goalRatio(new int[] {f1, f2, f3}) != -1) {
                        answer = new int[] {f1, f2, f3, goalRatio(new int[] {f1, f2, f3})};
                        break bruteForce;
                    }
                }
            }
        }

        PrintWriter written = new PrintWriter("stamps.out");
        if (answer != null) {
            written.printf("%s %s %s %s%n", answer[0], answer[1], answer[2], answer[3]);
            System.out.printf("%s %s %s %s%n", answer[0], answer[1], answer[2], answer[3]);
        } else {
            written.println("NONE");
            System.out.println("NONE");
        }
        written.close();
        System.out.printf("holy frick it took %d ms%n", System.currentTimeMillis() - start);
    }

    // this function returns -1 for invalid ratios
    private static int goalRatio(int[] amounts) {
        assert amounts.length == FEED_NUM;
        int[] totalAmount = new int[NUTRIENT_NUM];
        for (int a = 0; a < FEED_NUM; a++) {  // calculate how much feed this would give
            for (int n = 0; n < NUTRIENT_NUM; n++) {
                totalAmount[n] += mixes[a][n] * amounts[a];
            }
        }

        if (totalAmount[0] != 0 && goal[0] == 0 || totalAmount[0] % goal[0] != 0) {  // set the target by the first nutrient
            return -1;
        }
        int target = totalAmount[0] / goal[0];

        for (int a = 1; a < FEED_NUM; a++) {
            if (totalAmount[a] == 0 && goal[a] == 0) {  // if both are 0 it can take on any value i mean
                continue;
            }
            if ((totalAmount[a] != 0 && goal[a] == 0) ||
                    (totalAmount[a] / (float) goal[a] != target)) {
                return -1;
            }
        }
        return target;
    }
}
