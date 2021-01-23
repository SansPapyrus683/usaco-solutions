package official.o2015.dec.gold.trippingBessie;

import java.io.*;
import java.util.*;

/**
 * 2015 dec gold
 * 0 = red, impassable
 * 1 = pink, normal
 * 2 = orange, smell like oranges
 * 3 = blue, only walkable if oranges
 * 4 = purple, slides (each slide counts as a move) and removes smell
 */
public class Dream {
    private static final int[] CHANGE_R = new int[]{1, -1, 0, 0};
    private static final int[] CHANGE_C = new int[]{0, 0, 1, -1};

    // i swear i will go to james gosling's house and personally beg him to implement a pair class
    private static class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("dream.in"));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int rows = Integer.parseInt(initial.nextToken());
        int cols = Integer.parseInt(initial.nextToken());
        int[][] grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            grid[r] = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int[][][] distances = new int[rows][cols][2];  // 3d to implement whether we smell like oranges or not
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                distances[r][c] = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE};
            }
        }
        distances[0][0][0] = 0;
        PriorityQueue<Pair<int[], Boolean>> frontier =
                new PriorityQueue<>(Comparator.comparingInt(p -> distances[p.first[0]][p.first[1]][p.second ? 1 : 0]));
        frontier.add(new Pair<>(new int[] {0, 0}, false));
        while (!frontier.isEmpty()) {
            Pair<int[], Boolean> curr = frontier.poll();
            if (curr.first[0] == rows - 1 && curr.first[1] == cols - 1) {  // if we're at the end, don't do neighbors
                continue;
            }
            int rnCost = distances[curr.first[0]][curr.first[1]][curr.second ? 1 : 0];
            for (Pair<int[], Boolean> n : neighbors(curr, grid)) {
                int newCost = rnCost + n.first[2];
                if (newCost < distances[n.first[0]][n.first[1]][n.second ? 1 : 0]) {
                    frontier.add(n);  // don't worry, that third element in the neighbor won't do anything
                    distances[n.first[0]][n.first[1]][n.second ? 1 : 0] = newCost;
                }
            }
        }

        int minMoves = Arrays.stream(distances[rows - 1][cols - 1]).min().getAsInt() == Integer.MAX_VALUE ? -1
                : Arrays.stream(distances[rows - 1][cols - 1]).min().getAsInt();
        PrintWriter written = new PrintWriter("dream.out");
        written.println(minMoves);
        written.close();
        System.out.println(minMoves);
        System.out.printf("fricc yeah an undertale reference (WAIT THIS WAS IN 2015???) - %d ms%n", System.currentTimeMillis() - start);
    }

    private static ArrayList<Pair<int[], Boolean>> neighbors(Pair<int[], Boolean> rn, int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;
        ArrayList<Pair<int[], Boolean>> valid = new ArrayList<>();
        int[] rnPos = rn.first;
        for (int i = 0; i < 4; i++) {
            int r = rnPos[0] + CHANGE_R[i];
            int c = rnPos[1] + CHANGE_C[i];
            int tileType;
            if (0 <= r && r < height && 0 <= c && c < width && (tileType = grid[r][c]) != 0) {
                if (tileType == 1) {
                    valid.add(new Pair<>(new int[] {r, c, 1}, rn.second));
                } else if (tileType == 2) {
                    valid.add(new Pair<>(new int[] {r, c, 1}, true));
                } else if (tileType == 3 && rn.second) {
                    valid.add(new Pair<>(new int[] {r, c, 1}, true));  // i mean if she's here she def smells, so...
                } else if (tileType == 4) {
                    Pair<int[], Integer> slideInfo = slide(new Pair<>(new int[] {r, c}, rn.second), grid, i);
                    int[] slidePos = slideInfo.first;
                    // +1 because the slideInfo started AT the purple tile, so we have to get there first
                    valid.add(new Pair<>(new int[] {slidePos[0], slidePos[1], 1 + slideInfo.second}, grid[slidePos[0]][slidePos[1]] == 2));
                }
            }
        }
        return valid;
    }

    private static Pair<int[], Integer> slide(Pair<int[], Boolean> rn, int[][] grid, int direction) {
        int height = grid.length;
        int width = grid[0].length;
        int[] rnPos = rn.first;
        if (grid[rnPos[0]][rnPos[1]] != 4) {
            throw new IllegalArgumentException("bruh if you start sliding shouldn't you be on a purple tile lol");
        }
        int distance = 0;
        while (true) {
            int[] nextPos = new int[] {rnPos[0] + CHANGE_R[direction], rnPos[1] + CHANGE_C[direction]};
            distance++;
            int tileType;
            // if we're out of bounds or we hit an impassable tile
            if (nextPos[0] < 0 || nextPos[0] >= height || nextPos[1] < 0 || nextPos[1] >= width ||
                    (tileType = grid[nextPos[0]][nextPos[1]]) == 0 || (tileType == 3 && !rn.second)) {
                distance--;  // oops we processed too far, backtrack a bit
                break;
            } else if (tileType == 1 || tileType == 2) {
                rnPos = nextPos;
                break;
            }
            rnPos = nextPos;
        }
        return new Pair<>(rnPos, distance);
    }
}
