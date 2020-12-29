package official.o2017.feb.silver.cowKill3;

import java.io.*;
import java.util.*;

// 2017 feb silver
public class CountCross {
    private static final int[] CHANGE_X = new int[]{1, -1, 0, 0};
    private static final int[] CHANGE_Y = new int[]{0, 0, 1, -1};

    private static class Pair<T1, T2> {  // CURSE YOU JAVA!!!!
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("countcross.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());  // some random placeholder code
        int side = Integer.parseInt(initial.nextToken());
        int cowNum = Integer.parseInt(initial.nextToken());
        int roadNum = Integer.parseInt(initial.nextToken());
        Pair<int[], boolean[][]>[] cows = new Pair[cowNum];
        ArrayList<int[]>[][] roadedAdjacents = new ArrayList[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                roadedAdjacents[i][j] = new ArrayList<>();
            }
        }
        for (int r = 0; r < roadNum; r++) {
            int[] road = Arrays.stream(read.readLine().split(" ")).mapToInt(p -> Integer.parseInt(p) - 1).toArray();
            roadedAdjacents[road[0]][road[1]].add(new int[] {road[2], road[3]});
            roadedAdjacents[road[2]][road[3]].add(new int[] {road[0], road[1]});
        }
        for (int c = 0; c < cowNum; c++) {
            cows[c] = new Pair<int[], boolean[][]>(Arrays.stream(read.readLine().split(" ")).mapToInt(p -> Integer.parseInt(p) - 1).toArray(), null);
        }


        for (Pair<int[], boolean[][]> c : cows) {
            // calculate all the places where the cow can visit without crossing a road
            c.second = canVisit(c.first, roadedAdjacents);
        }
        int distantPairs = 0;
        for (int c1 = 0; c1 < cowNum; c1++) {
            for (int c2 = c1 + 1; c2 < cowNum; c2++) {
                // if the 2nd cow isn't in the 1st cow's "domain", they're distant
                if (!cows[c1].second[cows[c2].first[0]][cows[c2].first[1]]) {
                    distantPairs++;
                }
            }
        }

        PrintWriter written = new PrintWriter("countcross.out");
        written.println(distantPairs);
        written.close();
        System.out.println(distantPairs);
        System.out.printf("bruh why did you run this code that took %d ms lol%n", System.currentTimeMillis() - start);
    }

    static boolean arrayInArrayList(ArrayList<int[]> checkIn, int[] checkFor) {
        for (int[] i : checkIn) {
            if (Arrays.equals(checkFor, i)) {
                return true;
            }
        }
        return false;
    }

    static boolean[][] canVisit(int[] cow, ArrayList<int[]>[][] roadedAdjacents) {
        boolean[][] visited = new boolean[roadedAdjacents.length][roadedAdjacents[0].length];
        visited[cow[0]][cow[1]] = true;
        ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(cow));

        while (!frontier.isEmpty()) {  // just an ez bfs
            int[] current = frontier.poll();
            ArrayList<int[]> roaded = roadedAdjacents[current[0]][current[1]];
            for (int i = 0; i < 4; i++) {
                int r = current[0] + CHANGE_X[i];
                int c = current[1] + CHANGE_Y[i];
                // has to be within bounds, not visited, and we have to NOT cross a road to get here
                if (0 <= r && r < roadedAdjacents.length && 0 <= c && c < roadedAdjacents[0].length &&
                        !visited[r][c] && !arrayInArrayList(roaded, new int[] {r, c})) {
                    visited[r][c] = true;
                    frontier.add(new int[] {r, c});
                }
            }
        }
        return visited;
    }
}
