package official.o2020.dec.gold;

import java.io.*;
import java.util.*;

/**
 * 2020 dec gold
 * 4
 * 0 2
 * 2 3
 * 3 1
 * 1 0 should output 14
 */
public final class SPasture {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());  // io is exactly the same as silver lol
        int[][] cows = new int[cowNum][2];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        long start = System.currentTimeMillis();
        // 1 for the null subset
        int totalValid = 1 + validHorizontalSquares(cows, true);
        for (int[] c : cows) {
            int temp = c[0];
            c[0] = c[1];
            c[1] = temp;
        }
        totalValid += validHorizontalSquares(cows, false);
        System.out.println(totalValid);
        System.err.printf("why do you need it to be a square of all things: %d ms%n", System.currentTimeMillis() - start);
    }

    // "horizontal" squares are pastures where initially it's a rectangle
    // where width > height, so you have to expand it upwards so it's a sqaure
    private static int validHorizontalSquares(int[][] cows, boolean countEqual) {
        Arrays.sort(cows, Comparator.comparingInt(c -> c[0]));
        int enclosable = 0;
        for (int left = 0; left < cows.length; left++) {
            int[] leftCow = cows[left];
            TreeSet<Integer> seenYs = new TreeSet<>();
            for (int right = left + 1; right < cows.length; right++) {
                int[] rightCow = cows[right];
                int width = rightCow[0] - leftCow[0];
                if (width < Math.abs(rightCow[1] - leftCow[1])) {
                    seenYs.add(rightCow[1]);
                    continue;
                }
                // the bottom of the square if it was at its lowest and highest respectively
                int lowestLo = Math.max(leftCow[1], rightCow[1]) - width;
                int highestLo = Math.min(leftCow[1], rightCow[1]);

                ArrayList<Integer> ysBetween = new ArrayList<>(seenYs);
                // use 2 pointers to count the amount of distinct squares with these 2 at the very edge
                int bottom = 0;
                int top = -1;
                // get the lowest and highest y value in the current sqaure
                for (; bottom < ysBetween.size() && ysBetween.get(bottom) < lowestLo; bottom++);
                for (; top + 1 < ysBetween.size() && ysBetween.get(top + 1) <= Math.max(leftCow[1], rightCow[1]); top++);
                while (true) {
                    int lowest = Math.min(
                            Math.min(leftCow[1], rightCow[1]),
                            bottom < ysBetween.size() ? ysBetween.get(bottom) : Integer.MAX_VALUE
                    );
                    int highest = Math.max(
                            Math.max(leftCow[1], rightCow[1]),
                            0 <= top && top < ysBetween.size() ? ysBetween.get(top) : Integer.MIN_VALUE
                    );
                    // if they get bounded the same, then we count it conditionally
                    if (highest - lowest != width || countEqual) {
                        enclosable++;
                    }
                    // these two are the bottom of the square for if we leave out the bottom or if we include the top
                    int excludeBottom = bottom < ysBetween.size() ? ysBetween.get(bottom) + 1 : Integer.MAX_VALUE;
                    int includeTop = top + 1 < ysBetween.size() ? ysBetween.get(top + 1) - width : Integer.MAX_VALUE;
                    if (Math.min(excludeBottom, includeTop) > highestLo) {
                        break;  // stop if we go past the limit for the bottom of the square
                    }
                    if (excludeBottom < includeTop) {  // move the bottom and top (or both) depending on which occurs first
                        bottom++;
                    } else if (includeTop < excludeBottom) {
                        top++;
                    } else {
                        bottom++;
                        top++;
                    }
                }
                seenYs.add(rightCow[1]);
            }
        }
        // if we count the equal ones, each cow is its own subset as well
        return enclosable + (countEqual ? cows.length : 0);
    }
}
