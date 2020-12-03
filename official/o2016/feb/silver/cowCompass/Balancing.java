package official.o2016.feb.silver.cowCompass;

import java.io.*;
import java.util.*;

// 2016 feb silver
public class Balancing {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("balancing.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (cows[c][0] % 2 == 0 || cows[c][1] % 2 == 0) {
                throw new IllegalArgumentException("cow locations have to be odd my buddy chum pal");
            }
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        int lowerBound = 1;
        int upperBound = cowNum;
        int validSoFar = -1;
        while (lowerBound <= upperBound) {
            int toSearch = (lowerBound + upperBound) / 2;
            if (partitionPossible(cows, toSearch)) {
                upperBound = toSearch - 1;
                validSoFar = toSearch;
            } else {
                lowerBound = toSearch + 1;
            }
        }

        PrintWriter written = new PrintWriter("balancing.out");
        written.println(validSoFar);
        written.close();
        System.out.println(validSoFar);
        System.out.printf("What did it cost?... %d milliseconds.%n", System.currentTimeMillis() - start);
    }

    // this assumes the cows are sorted by x value (and it just assumes fences can be placed literally anywhere)
    private static boolean partitionPossible(int[][] cows, int maxCows) {
        int cowAt = 0;
        int cowsOnLeft = 0;
        while (cowAt < cows.length) {  // would this be a sweepline approach? idk
            cowsOnLeft++;
            int fenceAt = cows[cowAt++][0];  // set the fence to well, just in front of this (sorry for the kinda misleading name)
            while (cowAt < cows.length && cows[cowAt][0] <= fenceAt) {
                cowsOnLeft++;
                cowAt++;
            }
            if (cowsOnLeft <= 2 * maxCows && cows.length - cowsOnLeft <= 2 * maxCows &&
                    partitionByY(cows, fenceAt + 1, maxCows)) {
                return true;
            }
        }
        return false;
    }

    private static boolean partitionByY(int[][] cows, int xLine, int maxCows) {
        cows = cows.clone();
        Arrays.sort(cows, Comparator.comparingInt(c -> c[1]));
        int leftSide = (int) Arrays.stream(cows).filter(c -> c[0] < xLine).count();
        int rightSide = cows.length - leftSide;
        int cowAt = 0;
        int leftDownSide = 0;
        int rightDownSide = 0;
        while (cowAt < cows.length) {  // ok if we have a defined x line, now let's see if it's possible with the y val
            int yFence = cows[cowAt][1];
            if (cows[cowAt][0] < xLine) {
                leftDownSide++;
            } else {
                rightDownSide++;
            }
            cowAt++;
            while (cowAt < cows.length && cows[cowAt][1] <= yFence) {
                if (cows[cowAt][0] < xLine) {
                    leftDownSide++;
                } else {
                    rightDownSide++;
                }
                cowAt++;
            }
            if (leftDownSide <= maxCows && rightDownSide <= maxCows &&
                    leftSide - leftDownSide <= maxCows && rightSide - rightDownSide <= maxCows) {
                return true;
            }
        }

        return false;
    }
}
