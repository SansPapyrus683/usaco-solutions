package official.o2013.jan.gold.island;

import java.io.*;
import java.util.*;

// 2013 jan gold
public class Island {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("island.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rowNum = Integer.parseInt(initial.nextToken());
        int colNum = Integer.parseInt(initial.nextToken());
        String[] grid = new String[rowNum];
        for (int r = 0; r < rowNum; r++) {
            grid[r] = read.readLine();
            assert grid[r].length() == colNum;
        }

        List<List<int[]>> islands = new ArrayList<>();
        List<int[][]> islandDists = new ArrayList<>();
        boolean[][] visited = new boolean[rowNum][colNum];
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                if (visited[r][c] || grid[r].charAt(c) != 'X') {
                    continue;
                }
                visited[r][c] = true;

                List<int[]> island = new ArrayList<>(Collections.singletonList(new int[] {r, c}));
                ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {r, c}));
                while (!frontier.isEmpty()) {
                    int[] curr = frontier.pop();
                    for (int[] n : neighbors(curr[0], curr[1], rowNum, colNum)) {
                        if (!visited[n[0]][n[1]] && grid[n[0]].charAt(n[1]) == 'X') {
                            frontier.push(n);
                            island.add(n);
                            visited[n[0]][n[1]] = true;
                        }
                    }
                }
                islands.add(island);

                // ah, the things you can get away with when the bounds are small
                int[][] currDists = new int[rowNum][colNum];
                for (int i = 0; i < rowNum; i++) {
                    Arrays.fill(currDists[i], Integer.MAX_VALUE);
                }
                // this dijkstra's might not be 100% correct, but it acs so who cares
                PriorityQueue<int[]> oFrontier = new PriorityQueue<>(Comparator.comparingInt(p -> p[0]));
                for (int[] p : island) {
                    currDists[p[0]][p[1]] = 0;
                    oFrontier.add(new int[] {0, p[0], p[1]});
                }
                while (!oFrontier.isEmpty()) {
                    int[] curr = oFrontier.poll();
                    if (curr[0] != currDists[curr[1]][curr[2]]) {
                        continue;
                    }
                    for (int[] n  : neighbors(curr[1], curr[2], rowNum, colNum)) {
                        char cell = grid[n[0]].charAt(n[1]);
                        if (currDists[n[0]][n[1]] == Integer.MAX_VALUE && cell != '.') {
                            int cost = currDists[curr[1]][curr[2]] + (cell == 'S' ? 1 : 0);
                            if (cost < currDists[n[0]][n[1]]) {
                                currDists[n[0]][n[1]] = cost;
                                oFrontier.add(new int[] {cost, n[0], n[1]});
                            }
                        }
                    }
                }

                islandDists.add(currDists);
            }
        }

        int[][] dists = new int[islands.size()][islands.size()];
        for (int i1 = 0; i1 < islands.size(); i1++) {
            for (int i2 = i1 + 1; i2 < islands.size(); i2++) {
                int dist = Integer.MAX_VALUE;
                for (int[] p : islands.get(i2)) {
                    dist = Math.min(dist, islandDists.get(i1)[p[0]][p[1]]);
                }
                dists[i1][i2] = dists[i2][i1] = dist;
            }
        }

        int minDist = minHamiltonian(dists);

        PrintWriter written = new PrintWriter("island.out");
        written.println(minDist);
        written.close();
        System.out.println(minDist);
    }

    private static ArrayList<int[]> neighbors(int r, int c, int rowMax, int colMax) {
        final int[] rChanges = new int[] {0, 0, -1, 1};
        final int[] cChanges = new int[] {-1, 1, 0, 0};
        ArrayList<int[]> valid = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int newR = r + rChanges[i];
            int newC = c + cChanges[i];
            if (0 <= newR && newR < rowMax && 0 <= newC && newC < colMax) {
                valid.add(new int[] {newR, newC});
            }
        }
        return valid;
    }

    private static int minHamiltonian(int[][] dists) {
        int[][] subsetEnds = new int[1 << dists.length][dists.length];
        for (int ss = 0; ss < (1 << dists.length); ss++) {
            if (Integer.bitCount(ss) <= 1) {
                continue;
            }
            for (int end = 0; end < dists.length; end++) {
                if ((ss & (1 << end)) == 0) {
                    continue;
                }
                int prev = ss & ~(1 << end);
                subsetEnds[ss][end] = Integer.MAX_VALUE;
                for (int from = 0; from < dists.length; from++) {
                    if ((prev & (1 << from)) != 0) {
                        int cost = subsetEnds[prev][from] + dists[from][end];
                        subsetEnds[ss][end] = Math.min(subsetEnds[ss][end], cost);
                    }
                }
            }
        }

        return Arrays.stream(subsetEnds[(1 << dists.length) - 1]).min().getAsInt();
    }
}
