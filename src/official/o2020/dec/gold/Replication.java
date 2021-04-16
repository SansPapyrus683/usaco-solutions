package official.o2020.dec.gold;

import java.io.*;
import java.util.*;

// 2020 dec gold (no sample input bc it's so frickin huge)
public final class Replication {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int width = Integer.parseInt(initial.nextToken());
        int replTime = Integer.parseInt(initial.nextToken());

        char[][] farm = new char[width][width];
        for (int r = 0; r < width; r++) {
            farm[r] = read.readLine().toCharArray();
        }

        int[][] closestRockDist = new int[width][width];
        ArrayList<int[]> rockFrontier = new ArrayList<>();
        for (int r = 0; r < width; r++) {
            Arrays.fill(closestRockDist[r], -1);
            for (int c = 0; c < width; c++) {
                if (farm[r][c] == '#') {
                    rockFrontier.add(new int[] {r, c});
                    closestRockDist[r][c] = 0;
                }
            }
        }
        // do a bfs from all the rocks to see a square's closest distance from any rock
        while (!rockFrontier.isEmpty()) {
            ArrayList<int[]> inLine = new ArrayList<>();
            for (int[] p : rockFrontier) {
                for (int[] n : neighbors(p, width)) {
                    if (closestRockDist[n[0]][n[1]] == -1) {
                        inLine.add(n);
                        closestRockDist[n[0]][n[1]] = closestRockDist[p[0]][p[1]] + 1;
                    }
                }
            }
            rockFrontier = inLine;
        }

        boolean[][] visited = new boolean[width][width];
        ArrayList<int[]> frontier = new ArrayList<>();
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < width; c++) {
                if (farm[r][c] == 'S') {
                    visited[r][c] = true;
                    frontier.add(new int[] {r, c});
                }
            }
        }
        int time = 0;
        int robotRadius = 1;
        while (!frontier.isEmpty()) {
            ArrayList<int[]> inLine = new ArrayList<>();
            for (int[] p : frontier) {  // bfs to see which squares can have a robot as the center
                if (closestRockDist[p[0]][p[1]] < robotRadius) {
                    continue;  // damit, we moved into a rock while replicating
                }
                for (int[] n : neighbors(p, width)) {
                    if (!visited[n[0]][n[1]] && closestRockDist[n[0]][n[1]] >= robotRadius) {
                        visited[n[0]][n[1]] = true;
                        inLine.add(n);
                    }
                }
            }
            time++;
            if (time % replTime == 0) {
                robotRadius++;
            }
            frontier = inLine;
        }

        boolean[][] containsRobot = new boolean[width][width];
        HashMap<Integer, ArrayList<int[]>> possCenters = new HashMap<>();
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < width; c++) {
                if (visited[r][c]) {
                    containsRobot[r][c] = true;
                    if (!possCenters.containsKey(closestRockDist[r][c])) {
                        possCenters.put(closestRockDist[r][c], new ArrayList<>());
                    }
                    possCenters.get(closestRockDist[r][c]).add(new int[] {r, c});
                }
            }
        }
        // for all the possible centers, we can expand indefinitely until we hit a rock
        frontier = new ArrayList<>();
        for (int i = Collections.max(possCenters.keySet()); i >= 1; i--) {
            ArrayList<int[]> inLine = new ArrayList<>();
            // do the 3rd and final bfs lol
            for (int[] p : frontier) {
                for (int[] n : neighbors(p, width)) {
                    if (!containsRobot[n[0]][n[1]]) {
                        containsRobot[n[0]][n[1]] = true;
                        inLine.add(n);
                    }
                }
            }
            frontier = inLine;
            frontier.addAll(possCenters.getOrDefault(i, new ArrayList<>()));
        }
        int total = 0;
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < width; c++) {
                total += containsRobot[r][c] ? 1 : 0;
            }
        }
        System.out.println(total);
        System.err.printf("i have went through the stages of grief multiple times while doing this problem: %d ms%n", System.currentTimeMillis() - start);
    }

    private static ArrayList<int[]> neighbors(int[] pos, int width) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        if (pos[0] - 1 >= 0) {
            neighbors.add(new int[] {pos[0] - 1, pos[1]});
        }
        if (pos[0] + 1 < width) {
            neighbors.add(new int[] {pos[0] + 1, pos[1]});
        }
        if (pos[1] - 1 >= 0) {
            neighbors.add(new int[] {pos[0], pos[1] - 1});
        }
        if (pos[1] + 1 < width) {
            neighbors.add(new int[] {pos[0], pos[1] + 1});
        }
        return neighbors;
    }
}
