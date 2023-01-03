package official.o2016.jan.silver.angry;

import java.io.*;
import java.util.*;

// 2016 jan silver
public class Angry {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("angry.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int hayNum = Integer.parseInt(initial.nextToken());
        int cows = Integer.parseInt(initial.nextToken());
        int[] haybales = new int[hayNum];
        for (int h = 0; h < hayNum; h++) {
            haybales[h] = Integer.parseInt(read.readLine());
        }
        Arrays.sort(haybales);

        int validSoFar = -1;
        int lowerBound = 0;
        int upperBound = (int) Math.pow(10, 9);
        while (lowerBound <= upperBound) {
            int toSearch = (lowerBound + upperBound) / 2;
            if (canKillAll(cows, toSearch, haybales)) {
                upperBound = toSearch - 1;
                validSoFar = toSearch;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter("angry.out");
        written.println(validSoFar);
        written.close();
        System.out.println(validSoFar);
        System.out.printf("wait until rovio sues bessie for copyright infringement lol: %d ms%n", System.currentTimeMillis() - start);
    }

    static boolean canKillAll(int cows, int power, int[] haybales) {  // assumes haybales is sorted, which we did do above
        int toKill = 0;
        // greedily kill all of the haybales from left to right (don't know why, but it works lol)
        for (int c = 0; c < cows; c++) {
            int leftEdge = haybales[toKill];
            int rightEdge = leftEdge + 2 * power;
            while (haybales[toKill] <= rightEdge) {
                toKill++;
                if (toKill == haybales.length) {  // ok buddy we killed all of the haybales (3 stars)
                    return true;
                }
            }
        }
        return false;  // *pigs snorting sfx plays*
    }
}
