package official.o2017.usopen.silver.pervertedFJ;

import java.io.*;
import java.util.*;

// 2017 us open silver (i've watched enough hentai to know where this problem's going)
public class PairUp {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("pairup.in"));
        int distinctCowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[distinctCowNum][2];
        for (int c = 0; c < distinctCowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[1]));

        int maxTime = 0;  // so this algo pairs the top with the bottom (it's the only method that makes sense tbh)
        int leftCow = 0;
        int rightCow = distinctCowNum - 1;
        while (true) {  // so we move two pointers towards each other and stop when they meet
            int leftAmt = cows[leftCow][0];
            int rightAmt = cows[rightCow][0];
            maxTime = Math.max(maxTime, cows[leftCow][1] + cows[rightCow][1]);
            if (rightCow - leftCow <= 0) {
                break;
            }

            if (leftAmt < rightAmt) {
                cows[rightCow][0] -= leftAmt;
                cows[leftCow][0] = 0;
                leftCow++;
            } else if (leftAmt > rightAmt) {
                cows[leftCow][0] -= rightAmt;
                cows[rightCow][0] = 0;
                rightCow--;
            } else {
                cows[leftCow][0] = 0;
                cows[rightCow][0] = 0;
                leftCow++;
                rightCow--;
            }
        }

        PrintWriter written = new PrintWriter("pairup.out");
        written.println(maxTime);
        written.close();
        System.out.println(maxTime);
        System.out.printf("someone help me i'm being forced to write these against my will: %d ms%n", System.currentTimeMillis() - start);
    }
}
