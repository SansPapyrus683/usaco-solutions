package official.o2015.usopen.bronze.friendPaths;

import java.io.*;
import java.util.*;

// 2015 us open gold
public class PalPath {
    private static class Farm {
        public char[][] farm;
        private HashSet<String> possPaths = new HashSet<>();
        public Farm(char[][] farm) {
            this.farm = farm;
        }

        public HashSet<String> topPaths(int[] start) {  // go from the diagonal to the start
            possPaths = new HashSet<>();
            dfsTopPaths(start, "");
            return possPaths;
        }

        public HashSet<String> botPaths(int[] start) {  // go from the diagonal to the end @ the bottom
            possPaths = new HashSet<>();
            dfsBotPaths(start, "");
            return possPaths;
        }

        private void dfsBotPaths(int[] pos, String path) {
            path += farm[pos[0]][pos[1]];
            // it seems we've reached the end, let's record the path and move on
            if (pos[0] == farm.length - 1 && pos[1] == farm[0].length - 1) {
                possPaths.add(path);
                return;
            }
            if (pos[0] + 1 < farm.length) {  // move down a row
                dfsBotPaths(new int[] {pos[0] + 1, pos[1]}, path);
            }
            if (pos[1] + 1 < farm[0].length) {  // move right
                dfsBotPaths(new int[] {pos[0], pos[1] + 1}, path);
            }
        }

        private void dfsTopPaths(int[] pos, String path) {
            path += farm[pos[0]][pos[1]];
            if (pos[0] == 0 && pos[1] == 0) {
                possPaths.add(path);
                return;
            }
            if (pos[0] - 1 >= 0) {  // move up a row
                dfsTopPaths(new int[] {pos[0] - 1, pos[1]}, path);
            }
            if (pos[1] - 1 >= 0) {  // move left
                dfsTopPaths(new int[] {pos[0], pos[1] - 1}, path);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("palpath.in"));
        int width = Integer.parseInt(read.readLine());
        char[][] grid = new char[width][width];
        for (int r = 0; r < width; r++) {
            grid[r] = read.readLine().toUpperCase().toCharArray();
        }

        Farm farm = new Farm(grid);
        HashSet<String> allPaths = new HashSet<>();
        for (int r = 0; r < width; r++) {
            int[] diagPoint = new int[] {r, width - 1 - r};
            HashSet<String> topPaths = farm.topPaths(diagPoint);
            // it has to be a palindrome if a sequence can be achieved by moving to the start and the end
            topPaths.retainAll(farm.botPaths(diagPoint));
            allPaths.addAll(topPaths);
        }

        PrintWriter written = new PrintWriter("palpath.out");
        written.println(allPaths.size());
        written.close();
        System.out.println(allPaths.size());
        System.out.printf("all i know is pain: %d ms%n", System.currentTimeMillis() - start);
    }
}
