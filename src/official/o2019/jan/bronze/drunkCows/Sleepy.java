package official.o2019.jan.bronze.drunkCows;

import java.io.*;
import java.util.*;

// 2019 jan bronze
public class Sleepy {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("sleepy.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[] cows = Arrays.stream(read.readLine().split(" ")).mapToInt(c -> Integer.parseInt(c) - 1).toArray();
        if (cows.length != cowNum) {
            throw new IllegalArgumentException("look man the cow numbers you're giving me are inconsistent");
        }

        int last = Integer.MAX_VALUE;
        int badUpTo = cowNum - 1;
        for (; badUpTo >= 0; badUpTo--) {
            if (cows[badUpTo] > last) {
                break;
            }
            last = cows[badUpTo];
        }
        badUpTo++;  // we went too far for on time, so let's just back up one

        PrintWriter written = new PrintWriter("sleepy.out");
        written.println(badUpTo);
        written.close();
        System.out.println(badUpTo);
        System.out.printf("damit fj don't let your cows near vodka: %d ms%n", System.currentTimeMillis() - start);
    }
}
