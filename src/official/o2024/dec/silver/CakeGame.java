package official.o2024.dec.silver;

import java.io.*;
import java.util.Arrays;

/** 2024 dec silver */
public class CakeGame {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            int cakeNum = Integer.parseInt(read.readLine());
            assert cakeNum % 2 == 0;
            int[] cakes = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            assert cakes.length == cakeNum;

            long currWindow = 0;
            for (int i = 0; i < cakeNum / 2 + 1; i++) {
                currWindow += cakes[i];
            }
            long minWindow = currWindow;
            long totalCakes = currWindow;
            for (int i = cakeNum / 2 + 1; i < cakeNum; i++) {
                totalCakes += cakes[i];
                currWindow = currWindow + cakes[i] - cakes[i - cakeNum / 2 - 1];
                minWindow = Math.min(minWindow, currWindow);
            }

            System.out.printf("%d %d\n", minWindow, totalCakes - minWindow);
        }
    }
}
