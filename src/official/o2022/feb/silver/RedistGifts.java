package official.o2022.feb.silver;

import java.io.*;
import java.util.*;

/**
 * 2022 feb silver
 * 4
 * 1 2 3 4
 * 1 3 2 4
 * 1 2 3 4
 * 1 2 3 4 should output 1, 3, 2, and 4, each on a new line
 */
public final class RedistGifts {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int cowNum = Integer.parseInt(read.readLine());

        ArrayList<Integer>[] takeFrom = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            takeFrom[c] = new ArrayList<>();
        }
        for (int c = 0; c < cowNum; c++) {
            int[] prefs = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int g : prefs) {
                g--;
                if (g == c) {
                    break;
                }
                takeFrom[c].add(g);
            }
        }

        boolean[][] visitable = new boolean[cowNum][cowNum];
        for (int c = 0; c < cowNum; c++) {
            visitable[c][c] = true;
            ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(c));
            visitable[c][c] = true;
            while (!frontier.isEmpty()) {
                int curr = frontier.poll();
                for (int n : takeFrom[curr]) {
                    if (!visitable[c][n]) {
                        visitable[c][n] = true;
                        frontier.add(n);
                    }
                }
            }
        }

        int[] best = new int[cowNum];
        for (int c = 0; c < cowNum; c++) {
            best[c] = c;  // default
            for (int g : takeFrom[c]) {
                if (visitable[g][c]) {
                    best[c] = g;
                    break;
                }
            }
        }

        // love these neat oneliners java can sometimes do
        Arrays.stream(best).forEach(g -> System.out.println(g + 1));
    }
}
