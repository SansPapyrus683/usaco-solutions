package official.o2016.dec.gold.corpseCheck;

import java.io.*;
import java.util.*;

// 2016 dec gold
public class Checklist {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("checklist.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int holsteinNum = Integer.parseInt(initial.nextToken());
        int guernseyNum = Integer.parseInt(initial.nextToken());
        int[][] holsteins = new int[holsteinNum][2];
        int[][] guernseys = new int[guernseyNum][2];
        for (int h = 0; h < holsteinNum; h++) {
            holsteins[h] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        for (int g = 0; g < guernseyNum; g++) {
            guernseys[g] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // this[i][j][k] = min energy AFTER checking i holsteins and j guernseys and ending at k (0 = h, 1 = g)
        int[][][] minEnergy = new int[holsteinNum + 1][guernseyNum + 1][2];
        for (int h = 0; h < holsteinNum + 1; h++) {
            for (int g = 0; g < guernseyNum + 1; g++) {  // you've all seen c++, now get ready for... g++
                Arrays.fill(minEnergy[h][g], Integer.MAX_VALUE);
            }
        }

        // we start with the first one already checked
        minEnergy[1][0][0] = 0;
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> minEnergy[s[0]][s[1]][s[2]]));
        frontier.add(new int[] {1, 0, 0});
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();

            int[] cowAt = curr[2] == 1 ? guernseys[Math.max(curr[1] - 1, 0)] : holsteins[Math.max(curr[0] - 1, 0)];
            int currCost = minEnergy[curr[0]][curr[1]][curr[2]];
            if (curr[0] < holsteinNum) {
                int[] next = new int[] {curr[0] + 1, curr[1], 0};
                int newCost = currCost + cost(cowAt, holsteins[next[0] - 1]);
                if (newCost < minEnergy[next[0]][next[1]][next[2]]) {
                    minEnergy[next[0]][next[1]][next[2]] = newCost;
                    frontier.add(next);
                }
            }
            if (curr[1] < guernseyNum) {
                int[] next = new int[] {curr[0], curr[1] + 1, 1};
                int newCost = currCost + cost(cowAt, guernseys[next[1] - 1]);
                if (newCost < minEnergy[next[0]][next[1]][next[2]]) {
                    minEnergy[next[0]][next[1]][next[2]] = newCost;
                    frontier.add(next);
                }
            }
        }

        PrintWriter written = new PrintWriter("checklist.out");
        written.println(minEnergy[holsteinNum][guernseyNum][0]);  // we want the one where he ends at the holstein
        written.close();
        System.out.println(minEnergy[holsteinNum][guernseyNum][0]);
        System.out.printf("in that span of %d ms i may have committed a war crime%n", System.currentTimeMillis() - start);
    }

    private static int cost(int[] from, int[] to) {
        return (int) Math.pow(from[0] - to[0], 2) + (int) Math.pow(from[1] - to[1], 2);
    }
}
