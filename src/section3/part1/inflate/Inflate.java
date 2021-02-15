/*
ID: kevinsh4
TASK: inflate
LANG: JAVA
 */
package section3.part1.inflate;

import java.io.*;
import java.util.*;

public final class Inflate {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("loScores.txt"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int totalMins = Integer.parseInt(initial.nextToken());
        int problemNum = Integer.parseInt(initial.nextToken());
        int[][] problems = new int[problemNum][2];
        for (int p = 0; p < problemNum; p++) {
            // points, minutes
            problems[p] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int ans = bestScore(totalMins, problems);  // see python version for kinda? doc comments
        PrintWriter written = new PrintWriter("job.out");
        written.println(ans);
        written.close();
        System.out.println(ans);
        System.out.printf("i'm running out of ideas for these bruh: %d ms%n", System.currentTimeMillis() - start);
    }

    private static int bestScore(int limit, int[][] problems) {
        int[] bestScores = new int[limit + 1];
        for (int[] p : problems) {
            for (int t = p[1]; t < limit + 1; t++) {
                bestScores[t] = Math.max(bestScores[t], bestScores[t - p[1]] + p[0]);
            }
        }
        return Arrays.stream(bestScores).max().getAsInt();
    }
}
