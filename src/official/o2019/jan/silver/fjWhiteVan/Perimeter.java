package official.o2019.jan.silver.fjWhiteVan;

import java.io.*;
import java.util.*;

// 2019 jan silver
public class Perimeter {
    private static class Blob {
        private final int[] rChange = new int[] {1, -1, 0, 0};
        private final int[] cChange = new int[] {0, 0, 1, -1};

        private final int side;
        private final boolean[][] iceCream;
        public int area;
        public ArrayList<int[]> points = new ArrayList<>();
        public Blob(int area, int sideLen, boolean[][] iceCream) {
            this.area = area;
            this.iceCream = iceCream;
            side = sideLen;
        }

        public int perimeter() {
            int perim = 0;
            for (int[] pt : points) {
                int r = pt[0], c = pt[1];
                if (iceCream[r][c]) {
                    for (int i = 0; i < 4; i++) {
                        if (r + rChange[i] < 0 || r + rChange[i] >= side ||
                                c + cChange[i] < 0 || c + cChange[i] >= side ||
                                !iceCream[r + rChange[i]][c + cChange[i]]) {
                            perim++;
                        }
                    }
                }
            }
            return perim;
        }
    }

    static int side;
    static boolean[][] iceCream;
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("perimeter.in"));
        side  = Integer.parseInt(read.readLine());
        iceCream = new boolean[side][side];
        for (int r = 0; r < side; r++) {
            int col = 0;
            for (char c : read.readLine().toCharArray()) {
                iceCream[r][col] = c == '#';
                col++;
            }
        }

        int maxArea = 0;
        int areaPerimeter = 0;
        visited = new boolean[side][side];
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                if (!visited[r][c] && iceCream[r][c]) {
                    Blob blob = expand(new int[] {r, c});

                    if (blob.area > maxArea) {
                        maxArea = blob.area;
                        areaPerimeter = blob.perimeter();
                    } else if (blob.area == maxArea) {  // ok, but the perimeter might be smaller...
                        areaPerimeter = Math.min(areaPerimeter, blob.perimeter());
                    }
                }
            }
        }

        PrintWriter written = new PrintWriter("perimeter.out");
        written.printf("%s %s%n", maxArea, areaPerimeter);
        written.close();
        System.out.printf("%s %s%n", maxArea, areaPerimeter);
        System.out.printf("ding ding ding your program has taken %d ms (git gud lol)%n", System.currentTimeMillis() - start);
    }

    static Blob expand(int[] from) {  // just a simple bfs, with some extra stuff to keep track of the points added
        // start with that one "from" blob
        Blob blob = new Blob(1, side, iceCream);
        blob.points.add(from);
        Queue<int[]> frontier = new ArrayDeque<>(Collections.singletonList(from));
        visited[from[0]][from[1]] = true;
        while (!frontier.isEmpty()) {
            int[] curr = frontier.poll();
            for (int[] n : neighbors(curr)) {
                if (!visited[n[0]][n[1]] && iceCream[n[0]][n[1]]) {
                    blob.area++;
                    blob.points.add(n);
                    frontier.add(n);
                    visited[n[0]][n[1]] = true;
                }
            }
        }
        return blob;
    }

    static ArrayList<int[]> neighbors(int[] pt) {
        ArrayList<int[]> valid = new ArrayList<>();  // it don't look pretty, and it don't work pretty, but it works.
        if (pt[0] - 1 >= 0) {
            valid.add(new int[] {pt[0] - 1, pt[1]});
        }
        if (pt[0] + 1 < side) {
            valid.add(new int[] {pt[0] + 1, pt[1]});
        }
        if (pt[1] - 1 >= 0) {
            valid.add(new int[] {pt[0], pt[1] - 1});
        }
        if (pt[1] + 1 < side) {
            valid.add(new int[] {pt[0], pt[1] + 1});
        }
        return valid;
    }
}
