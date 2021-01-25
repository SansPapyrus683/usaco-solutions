package official.o2017.feb.gold.cookedCows2;

import java.io.*;
import java.util.*;

// 2017 feb gold
public final class NoCross {
    private static final int THRESHOLD = 4;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("nocross.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int fieldNum = Integer.parseInt(initial.nextToken());
        int[] firstFields = new int[fieldNum];
        int[] secondIDToPos = new int[fieldNum];  // tbh we don't really need the second field values
        for (int i = 0; i < fieldNum; i++) {
            firstFields[i] = Integer.parseInt(read.readLine()) - 1;
        }
        for (int i = 0; i < fieldNum; i++) {
            secondIDToPos[Integer.parseInt(read.readLine()) - 1] = i;
        }

        // maximum crosswalks we can draw given the ending position of the last crosswalk
        int[] maxWithEnd = new int[fieldNum];
        // set the base case
        for (int f = Math.max(0, firstFields[0] - THRESHOLD); f <= Math.min(fieldNum - 1, firstFields[0] + THRESHOLD); f++) {
            maxWithEnd[secondIDToPos[f]]++;
        }
        for (int i = 1; i < fieldNum; i++) {
            int[] updated = maxWithEnd.clone();
            for (int f = Math.max(0, firstFields[i] - THRESHOLD); f <= Math.min(fieldNum - 1, firstFields[i] + THRESHOLD); f++) {
                updated[secondIDToPos[f]] = Math.max(rangeMax(maxWithEnd, 0, secondIDToPos[f]) + 1, maxWithEnd[secondIDToPos[f]]);
            }
            for (int f = 0; f < fieldNum; f++) {
                maxWithEnd[f] = Math.max(maxWithEnd[f], updated[f]);
            }
        }

        int max = Arrays.stream(maxWithEnd).max().getAsInt();
        PrintWriter written = new PrintWriter("nocross.out");
        written.println(max);
        written.close();
        System.out.println(max);
        System.out.printf("%d ms and we are DONE FRICK YEA%n", System.currentTimeMillis() - start);
    }

    static int rangeMax(int[] arr, int from, int to) {
        int max = 0;
        for (int i = from; i < to; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }
}
