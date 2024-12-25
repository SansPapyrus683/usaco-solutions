package official.o2024.dec.bronze;

import java.io.*;
import java.util.*;

/** 2024 dec bronze */
public class CheeseBlock {
    private static class Point {
        private final int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                Point pObj = (Point) obj;
                return x == pObj.x && y == pObj.y && z == pObj.z;
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int side = Integer.parseInt(initial.nextToken());
        int holeNum = Integer.parseInt(initial.nextToken());
        Point[] holes = new Point[holeNum];
        for (int h = 0; h < holeNum; h++) {
            StringTokenizer hole = new StringTokenizer(read.readLine());
            holes[h] = new Point(
                    Integer.parseInt(hole.nextToken()),
                    Integer.parseInt(hole.nextToken()),
                    Integer.parseInt(hole.nextToken())
            );
        }

        int validHoles = 0;
        Set<Point> holeSet = new HashSet<>(Arrays.asList(holes));
        // use ints instead of booleans bc STUPID JAVA DOESN'T CAST
        int[][] x = new int[side][side];
        int[][] y = new int[side][side];
        int[][] z = new int[side][side];
        // yeah, i got nothing. this code sucks, i'm sorry.
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                int valid = 1;
                for (int k = 0; k < side; k++) {
                    if (!holeSet.contains(new Point(k, i, j))) {
                        valid = 0;
                        break;
                    }
                }
                x[i][j] = valid;
                validHoles += valid;

                valid = 1;
                for (int k = 0; k < side; k++) {
                    if (!holeSet.contains(new Point(i, k, j))) {
                        valid = 0;
                        break;
                    }
                }
                y[i][j] = valid;
                validHoles += valid;

                valid = 1;
                for (int k = 0; k < side; k++) {
                    if (!holeSet.contains(new Point(i, j, k))) {
                        valid = 0;
                        break;
                    }
                }
                z[i][j] = valid;
                validHoles += valid;
            }
        }

        int[] brickNum = new int[holeNum];
        brickNum[holeNum - 1] = validHoles;
        for (int i = holeNum - 1; i > 0; i--) {
            Point h = holes[i];
            brickNum[i - 1] = brickNum[i] - x[h.y][h.z] - y[h.x][h.z] - z[h.x][h.y];
            x[h.y][h.z] = y[h.x][h.z] = z[h.x][h.y] = 0;
        }

        Arrays.stream(brickNum).forEach(System.out::println);
    }
}
