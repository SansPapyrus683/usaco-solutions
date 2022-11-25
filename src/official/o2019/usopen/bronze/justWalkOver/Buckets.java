package official.o2019.usopen.bronze.justWalkOver;

import java.io.*;
import java.util.*;

// 2019 us open bronze (no idea why i decided to use java, force of habit i guess)
public class Buckets {
    private static final int[] CHANGE_R = {0, 0, 1, -1};
    private static final int[] CHANGE_C = {1, -1, 0, 0};
    private static final int WIDTH = 10;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("buckets.in"));
        char[][] grid = new char[WIDTH][WIDTH];
        int[] lake = new int[] {-1, -1};
        for (int r = 0; r < WIDTH; r++) {
            int col = 0;
            for (char c : read.readLine().toCharArray()) {
                c = Character.toUpperCase(c);
                if (c == 'L') {
                    lake = new int[] {r, col};
                }
                grid[r][col++] = c;
            }
        }
        assert lake[0] != -1;

        ArrayList<int[]> frontier = new ArrayList<>(Collections.singletonList(lake));
        boolean[][] visited = new boolean[WIDTH][WIDTH];
        visited[lake[0]][lake[1]] = true;
        int movesTaken = 0;
        expansion:
        while (!frontier.isEmpty()) {
            ArrayList<int[]> inLine = new ArrayList<>();
            for (int[] p : frontier) {
                if (grid[p[0]][p[1]] == 'B') {
                    break expansion;
                }
                for (int i = 0; i < 4; i++) {
                    int[] n = new int[] {p[0] + CHANGE_R[i], p[1] + CHANGE_C[i]};  // n for neighbor
                    if (0 <= n[0] && n[0] < WIDTH && 0 <= n[1] && n[1] < WIDTH
                            && grid[n[0]][n[1]] != 'R' && !visited[n[0]][n[1]]) {
                        inLine.add(n);
                        visited[n[0]][n[1]] = true;
                    }
                }
            }
            movesTaken++;
            frontier = inLine;
        }

        PrintWriter written = new PrintWriter("buckets.out");
        written.println(movesTaken - 1);  // -1 just because of how bfs works
        written.close();
        System.out.println(movesTaken - 1);
        System.out.printf("just call 911 i mean: %d ms%n", System.currentTimeMillis() - start);
    }
}
