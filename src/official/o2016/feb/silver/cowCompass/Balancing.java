package official.o2016.feb.silver.cowCompass;

import java.io.*;
import java.util.*;

// 2016 feb silver (this is REALLY inefficient but what can you do about it lol)
public final class Balancing {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("balancing.in"));
        // stringtokenizer for bronze accommodation
        int cowNum = Integer.parseInt(new StringTokenizer(read.readLine()).nextToken());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (cows[c][0] % 2 == 0 || cows[c][1] % 2 == 0) {
                throw new IllegalArgumentException("cow locations have to be odd my buddy chum pal");
            }
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        int mostEqual = Integer.MAX_VALUE;
        int vertLineAt = 0;  // these "at" variables are the closest point that's still ahead of the line
        while (vertLineAt < cowNum) {
            int xLine = cows[vertLineAt][0] + 1;
            mostEqual = Math.min(mostEqual, minPartition(xLine, cows));
            while (vertLineAt < cowNum && xLine > cows[vertLineAt][0]) {
                vertLineAt++;
            }
        }

        PrintWriter written = new PrintWriter("balancing.out");
        written.println(mostEqual);
        written.close();
        System.out.println(mostEqual);
        System.out.printf("What did it cost?... %d milliseconds.%n", System.currentTimeMillis() - start);
    }

    // given a vertical line, this find the optimal horizontal line
    private static int minPartition(int xLine, int[][] cows) {
        int[][] byY = cows.clone();
        Arrays.sort(byY, Comparator.comparingInt(c -> c[1]));

        ArrayList<int[]> leftSide = new ArrayList<>();
        ArrayList<int[]> rightSide = new ArrayList<>();
        for (int[] c : byY) {
            if (c[0] < xLine) {
                leftSide.add(c);
            } else if (c[0] > xLine) {
                rightSide.add(c);
            } else {
                throw new IllegalArgumentException("don't think cows can be on fences in this problem");
            }
        }

        int mostEqual = Integer.MAX_VALUE;
        int leftAt = 0;
        int rightAt = 0;
        int totalAt = 0;
        while (totalAt < cows.length) {
            int yLine = byY[totalAt][1] + 1;
            while (totalAt < cows.length && yLine > byY[totalAt][1]) {
                totalAt++;
            }
            while (leftAt < leftSide.size() && yLine > leftSide.get(leftAt)[1]) {
                leftAt++;
            }
            while (rightAt < rightSide.size() && yLine > rightSide.get(rightAt)[1]) {
                rightAt++;
            }
            int belowMax = Math.max(leftAt, rightAt);
            int aboveMax = Math.max(leftSide.size() - leftAt, rightSide.size() - rightAt);
            mostEqual = Math.min(mostEqual, Math.max(belowMax, aboveMax));
        }
        return mostEqual;
    }
}
