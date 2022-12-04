package official.o2016.dec.silver.moocast;

import java.io.*;
import java.util.*;

// 2016 dec silver
public class Moocast {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("moocast.in"));
        int cowNum = Integer.parseInt(read.readLine());
        int[][] cows = new int[cowNum][3];  // x, y, then power
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int mostReachable = 0;
        for (int c = 0; c < cowNum; c++) {
            mostReachable = Math.max(mostReachable, broadcastSize(c, cows));
        }

        PrintWriter written = new PrintWriter("moocast.out");
        written.println(mostReachable);
        written.close();
        System.out.println(mostReachable);
        System.out.printf("pong! it took %d ms!%n", System.currentTimeMillis() - start);
    }

    static int broadcastSize(int start, int[][] cows) {
        assert 0 <= start && start < cows.length;

        // just do a simple bfs to see how many cows we can reach
        ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(start));
        boolean[] reached = new boolean[cows.length];
        reached[start] = true;
        while (!frontier.isEmpty()) {
            int[] current = cows[frontier.poll()];
            for (int c = 0; c < cows.length; c++) {
                if (!reached[c] && current[2] >= distance(current, cows[c])) {
                    reached[c] = true;
                    frontier.add(c);
                }
            }
        }

        int visited = 0;
        for (boolean c : reached) {
            if (c) {
                visited++;
            }
        }
        return visited;
    }

    static float distance(int[] p1, int[] p2) {
        return (float) Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }
}
