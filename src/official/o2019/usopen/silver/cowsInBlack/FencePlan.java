package official.o2019.usopen.silver.cowsInBlack;

import java.io.*;
import java.util.*;

// 2019 usopen silver
public final class FencePlan {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("fenceplan.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int connectionNum = Integer.parseInt(initial.nextToken());
        int[][] cows = new int[cowNum][2];
        ArrayList<Integer>[] neighbors = new ArrayList[cowNum];
        for (int c = 0; c < cowNum; c++) {
            cows[c] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            neighbors[c] = new ArrayList<>();
        }
        for (int c = 0; c < connectionNum; c++) {
            StringTokenizer connection = new StringTokenizer(read.readLine());
            int c1 = Integer.parseInt(connection.nextToken()) - 1;
            int c2 = Integer.parseInt(connection.nextToken()) - 1;
            neighbors[c1].add(c2);
            neighbors[c2].add(c1);
        }

        boolean[] processed = new boolean[cowNum];
        int minPerimeter = Integer.MAX_VALUE;
        for (int c = 0; c < cowNum; c++) {
            if (processed[c]) {
                continue;
            }
            processed[c] = true;
            int[] boundaries = new int[] {cows[c][0], cows[c][0], cows[c][1], cows[c][1]};  // minx, maxx, miny, and maxy
            // i mean ez bfs for all the cows in the network, how hard can it be
            ArrayDeque<Integer> frontier = new ArrayDeque<>(Collections.singletonList(c));
            while (!frontier.isEmpty()) {
                int current = frontier.poll();
                for (int n : neighbors[current]) {
                    if (!processed[n]) {
                        frontier.add(n);
                        processed[n] = true;
                        boundaries[0] = Math.min(boundaries[0], cows[n][0]);
                        boundaries[1] = Math.max(boundaries[1], cows[n][0]);
                        boundaries[2] = Math.min(boundaries[2], cows[n][1]);
                        boundaries[3] = Math.max(boundaries[3], cows[n][1]);
                    }
                }
            }
            minPerimeter = Math.min(minPerimeter, 2 * (boundaries[1] - boundaries[0]) + 2 * (boundaries[3] - boundaries[2]));
        }

        PrintWriter written = new PrintWriter("fenceplan.out");
        written.println(minPerimeter);
        written.close();
        System.out.println(minPerimeter);
        System.out.printf("and they said it couldn't take %d ms...%n", System.currentTimeMillis() - start);
    }
}
