package official.o2023.dec.bronze;

import java.io.*;
import java.util.*;
public class CandyCane {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int candyNum = Integer.parseInt(initial.nextToken());
        long[] cows = Arrays.stream(read.readLine().split(" "))
                .mapToLong(Long::parseLong).toArray();
        assert cowNum == cows.length;
        int[] candy = Arrays.stream(read.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        assert candyNum == candy.length;

        for (int c : candy) {
            long currHeight = 0;  // how far the candy is from the ground
            for (int i = 0; i < cowNum; i++) {
                if (cows[i] >= currHeight) {
                    long eatAmt = Math.min(c - currHeight, cows[i] - currHeight);
                    cows[i] += eatAmt;
                    currHeight += eatAmt;
                }
                if (currHeight == c) {
                    break;
                }
            }
        }

        Arrays.stream(cows).forEach(System.out::println);
    }
}
