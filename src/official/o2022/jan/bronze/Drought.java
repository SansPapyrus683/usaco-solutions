package official.o2022.jan.bronze;

import java.io.*;
import java.util.*;

public class Drought {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int cowNum = Integer.parseInt(read.readLine());
            int[] cows = Arrays.stream(read.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            assert cows.length == cowNum;
            System.out.println(minFood(cows));
        }
    }

    private static long minFood(int[] cows) {
        cows = cows.clone();
        long food = 0;
        for (int c = 0; c < cows.length - 1; c++) {
            if (cows[c] < cows[c + 1]) {
                if (c == cows.length - 2) {
                    return -1;
                }
                int subAmt = cows[c + 1] - cows[c];
                cows[c + 1] -= subAmt;
                cows[c + 2] -= subAmt;
                food += subAmt;
                if (cows[c + 2] < 0) {
                    return -1;
                }
            } else if (cows[c] > cows[c + 1]) {
                if (c % 2 == 0) {
                    return -1;
                }
                int subAmt = cows[c] - cows[c + 1];
                // just pretend we subtracted from all the other cows, doesn't matter anyways
                cows[c] -= subAmt;
                food += (long) subAmt * ((c + 1) / 2);
            }
        }

        food *= 2;
        return food;
    }
}
