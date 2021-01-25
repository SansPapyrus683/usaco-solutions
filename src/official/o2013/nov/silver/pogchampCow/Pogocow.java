package official.o2013.nov.silver.pogchampCow;

import java.io.*;
import java.util.*;

public final class Pogocow {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("pogocow.in"));
        int targetNum = Integer.parseInt(read.readLine());
        int[][] targets = new int[targetNum][2];  // position and point value
        int farthestRight = 0;
        for (int t = 0; t < targetNum; t++) {
            targets[t] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            farthestRight = Math.max(farthestRight, targets[t][0]);
        }
        int leftToRightMax = maxPoints(targets);
        for (int t = 0; t < targetNum; t++) {  // "mirror" the points for the right to left approach
            targets[t][0] = farthestRight - targets[t][0];
        }
        int rightToLeftMax = maxPoints(targets);

        int totalMax = Math.max(leftToRightMax, rightToLeftMax);
        PrintWriter written = new PrintWriter("pogocow.out");
        written.println(totalMax);
        written.close();
        System.out.println(totalMax);
        System.out.printf("%d ms, thank you come again lol%n", System.currentTimeMillis() - start);
    }

    private static int maxPoints(int[][] targets) {
        Arrays.sort(targets, Comparator.comparingInt(t -> t[0]));
        int[] positions = new int[targets.length];
        for (int t = 0; t < targets.length; t++) {
            positions[t] = targets[t][0];
        }

        /*
         * (straight from the sol)
         * this[a][b] is the max score if we start at a, intending to jump to b next
         * note that b has to be STRICTLY greater than a (a == b conceptually doesn't make any sense if you think abt it)
         */
        int[][] bestScore = new int[targets.length][targets.length];
        // this[a][b] is the max of bestScore[a][b], bestScore[a][b + 1], all the way until the end
        int[][] overallMax = new int[targets.length][targets.length];
        int tLen = targets.length;  // just a shorthand, please don't kill me
        for (int start = 0; start < tLen - 1; start++) {  // process all possible starts (except the end)
            bestScore[start][tLen - 1] = targets[start][1] + targets[tLen - 1][1];
            overallMax[start][tLen - 1] = bestScore[start][tLen - 1];
        }

        for (int start = tLen - 1 - 1; start >= 0; start--) {
            for (int next = tLen - 1 - 1; next > start; next--) {
                // after jumping to next, what's the next one we can jump to?
                int afterNext = bisectLeft(positions, positions[next] + (positions[next] - positions[start]));
                if (afterNext >= targets.length) {  // ah frick, we're at a dead end
                    bestScore[start][next] = targets[start][1] + targets[next][1];
                } else {
                    bestScore[start][next] = targets[start][1] + overallMax[next][afterNext];
                }
                // just update the overall max according to those dp rules i stated above
                overallMax[start][next] = Math.max(bestScore[start][next], overallMax[start][next + 1]);
            }
        }
        int actualBest = 0;
        for (int i = 0; i < targets.length; i++) {
            for (int j = 0; j < targets.length; j++) {
                actualBest = Math.max(actualBest, bestScore[i][j]);
            }
        }
        return actualBest;
    }

    // straight from python's bisect module!
    private static int bisectLeft(int[] arr, int elem) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < elem) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
