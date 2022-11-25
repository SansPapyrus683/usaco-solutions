package official.o2016.feb.silver.cowCompass;

import java.io.*;
import java.util.*;

// 2016 feb silver (this is REALLY inefficient but what can you do about it lol)
public class Balancing {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("balancing.in"));
        // stringtokenizer for bronze accommodation
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            assert cows[c][0] % 2 == 1 && cows[c][1] % 2 == 1;
        }
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));

        int[][] byY = cows.clone();
        Arrays.sort(byY, Comparator.comparingInt(c -> c[1]));

        int mostEqual = Integer.MAX_VALUE;
        int vertLineAt = 0;  // these "at" variables are the closest point that's still ahead of the line
        while (vertLineAt < cowNum) {
            int xLine = cows[vertLineAt][0] + 1;
            mostEqual = Math.min(mostEqual, minPartition(xLine, byY));
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

    /*
     * given a vertical line, this find the optimal horizontal line
     * assumes the cows have been sorted by y-coordinate already
     */
    private static int minPartition(int xLine, int[][] cows) {
        ArrayList<int[]> leftSide = new ArrayList<>();
        ArrayList<int[]> rightSide = new ArrayList<>();
        for (int[] c : cows) {
            assert c[0] != xLine;
            if (c[0] < xLine) {
                leftSide.add(c);
            } else {
                rightSide.add(c);
            }
        }

        int mostEqual = Integer.MAX_VALUE;
        int leftAt = 0;
        int rightAt = 0;
        int totalAt = 0;
        while (totalAt < cows.length) {
            int yLine = cows[totalAt][1] + 1;
            while (totalAt < cows.length && yLine > cows[totalAt][1]) {
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
