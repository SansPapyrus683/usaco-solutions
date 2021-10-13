package official.o2016.usopen.silver.minecraftBessie;

import java.io.*;
import java.util.*;

// 2016 us open silver (this one's the intended sol)
public final class Diamonds {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("diamond.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int diamondNum = Integer.parseInt(initial.nextToken());
        int maxSizeDiff = Integer.parseInt(initial.nextToken());
        int[] diamonds = new int[diamondNum];
        for (int d = 0; d < diamondNum; d++) {
            diamonds[d] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(diamonds);

        // most diamonds we can fit using diamonds that < the size at this index
        int[] smallerMax = new int[diamondNum];
        // most diamonds we can fit using diamonds that >= the size at this index
        int[] largerMax = new int[diamondNum];
        // indices of the smallest and largest one according to the rules above
        int smallestFittable = 0;
        int largestFittable = 0;
        for (int d = 0; d < diamondNum; d++) {
            while (smallestFittable < d && diamonds[d - 1] - diamonds[smallestFittable] > maxSizeDiff) {
                smallestFittable++;
            }
            smallerMax[d] = d - smallestFittable;
            while (largestFittable + 1 < diamondNum && diamonds[largestFittable + 1] - diamonds[d] <= maxSizeDiff) {
                largestFittable++;
            }
            largerMax[d] = largestFittable - d + 1;  // +1 because d is included
        }

        // carry the stuff over
        for (int i = 1; i < diamondNum; i++) {
            smallerMax[i] = Math.max(smallerMax[i], smallerMax[i - 1]);
        }
        for (int i = diamondNum - 1 - 1; i >= 0; i--) {
            largerMax[i] = Math.max(largerMax[i], largerMax[i + 1]);
        }

        int max = 0;
        for (int i = 0; i < diamondNum; i++) {
            max = Math.max(max, smallerMax[i] + largerMax[i]);
        }

        PrintWriter written = new PrintWriter("diamond.out");
        written.println(max);
        written.close();
        System.out.println(max);
        System.out.printf("MIIIINNNNNE DIIIIAAAAAAAMOOOOOONNNNDSSS- %d ms lol%n", System.currentTimeMillis() - start);
    }
}