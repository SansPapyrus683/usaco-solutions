package official.o2016.jan.silver.ADHDorigins;

import java.io.*;
import java.util.*;

// 2016 jan silver (this code is an entire soup of bad practices lol)
public class Gates {
    private static final int BOUNDS = 2004;
    private static final int TRUE_BOUNDS = 2 * BOUNDS + 1;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("gates.in"));
        read.readLine();
        char[] directions = read.readLine().toCharArray();

        // have the fence set as lines instead of blocks, makes things alot easier
        int[] pos = new int[] {BOUNDS, BOUNDS};  // make everything positive, also makes things easier
        boolean[][] walls = new boolean[TRUE_BOUNDS][TRUE_BOUNDS];
        walls[pos[0]][pos[1]] = true;
        for (char d : directions) {
            int dirX = 0;
            int dirY = 0;
            if (d == 'N') {  // i'd use a switch statement but does that really matter?
                dirX = 1;
            } else if (d == 'S') {
                dirX = -1;
            } else if (d == 'W') {
                dirY = 1;
            } else if (d == 'E') {
                dirY = -1;
            }
            for (int i = 0; i < 2; i++) {  // because we have fences as blocks, double the dist. travelled
                pos[0] += dirX;
                pos[1] += dirY;
                walls[pos[0]][pos[1]] = true;
            }
        }

        int fenceRegions = 0;
        int[] changeX = new int[] {1, -1, 0, 0};
        int[] changeY = new int[] {0, 0, 1, -1};
        boolean[][] processed = new boolean[TRUE_BOUNDS][TRUE_BOUNDS];
        for (int x = 0; x < TRUE_BOUNDS; x++) {
            for (int y = 0; y < TRUE_BOUNDS; y++) {
                if (processed[x][y] || walls[x][y]) {
                    continue;
                }
                processed[x][y] = true;
                ArrayDeque<int[]> frontier = new ArrayDeque<>(Collections.singletonList(new int[] {x, y}));
                while (!frontier.isEmpty()) {  // bfs until we have every part of the region
                    int[] curr = frontier.poll();
                    int currX = curr[0];
                    int currY = curr[1];
                    for (int i = 0; i < 4; i++) {
                        int newX = currX + changeX[i];
                        int newY = currY + changeY[i];
                        if (0 <= newX && newX < TRUE_BOUNDS && 0 <= newY && newY < TRUE_BOUNDS &&
                                !walls[newX][newY] && !processed[newX][newY]) {
                            frontier.add(new int[] {newX, newY});
                            processed[newX][newY] = true;
                        }
                    }
                }
                fenceRegions++;
            }
        }

        // the answer if just amt of regions - 1 because with a gate we can only connect 2 regions
        PrintWriter written = new PrintWriter("gates.out");
        written.println(fenceRegions - 1);
        written.close();
        System.out.println(fenceRegions - 1);
        System.out.printf("not even the flash can beat this %d ms%n", System.currentTimeMillis() - start);
    }
}
