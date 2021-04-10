package official.o2021.usopen.gold;

import java.io.*;
import java.util.*;

/**
 * 2021 usopen gold
 * 5
 * 0 0
 * 0 4
 * 4 0
 * 1 1
 * 1 2 should output 96
 * so for this problem note that if we want to add a point to a bunch of other points the new point can only
 * 1. already be inside the big giant triangle that's already been formed by the 3 current outermost points or
 * 2. it has to be able to reach all the other vertices of the triangle from the outside
 */
public final class Permutations {
    private static final int MOD = (int) Math.pow(10, 9) + 7;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int pointNum = Integer.parseInt(read.readLine());
        Point[] points = new Point[pointNum];
        for (int p = 0; p < pointNum; p++) {
            StringTokenizer point = new StringTokenizer(read.readLine());
            points[p] = new Point(Integer.parseInt(point.nextToken()), Integer.parseInt(point.nextToken()));
        }

        // ans given i < j < k (indices of vertices of the large triangle) and the # of points added alr (0 when # < 3)
        long[][][][] permNum = new long[pointNum][pointNum][pointNum][pointNum + 1];
        ArrayList<int[]> triangles = new ArrayList<>();
        for (int p1 = 0; p1 < pointNum; p1++) {
            for (int p2 = p1 + 1; p2 < pointNum; p2++) {
                for (int p3 = p2 + 1; p3 < pointNum; p3++) {
                    if (signArea(points[p1], points[p2], points[p3]) == 0) {
                        throw new IllegalArgumentException("no 3 of the points should be collinear");
                    }
                    triangles.add(new int[] {p1, p2, p3});
                }
            }
        }
        triangles.sort(Comparator.comparing(t -> Math.abs(signArea(points[t[0]], points[t[1]], points[t[2]]))));

        long validPerms = 0;
        for (int[] t : triangles) {
            int inPts = 0;
            ArrayList<int[]> nextUp = new ArrayList<>();
            for (int p = 0; p < pointNum; p++) {
                if (inTriangle(points[p], points[t[0]], points[t[1]], points[t[2]])) {
                    inPts++;
                } else {
                    if (inTriangle(points[t[0]], points[p], points[t[1]], points[t[2]])) {
                        nextUp.add(new int[] {p, t[1], t[2]});
                    } else if (inTriangle(points[t[1]], points[t[0]], points[p], points[t[2]])) {
                        nextUp.add(new int[] {p, t[0], t[2]});
                    } else if (inTriangle(points[t[2]], points[t[0]], points[t[1]], points[p])) {
                        nextUp.add(new int[] {p, t[0], t[1]});
                    }
                }
            }
            for (int[] nt : nextUp) {
                Arrays.sort(nt);
            }

            permNum[t[0]][t[1]][t[2]][3] = 6;  // 3 * 2 * 1 = 6 ways to form the initial triangle
            for (int added = 3; added <= inPts; added++) {
                permNum[t[0]][t[1]][t[2]][added] += permNum[t[0]][t[1]][t[2]][added - 1] * (inPts - added + 1);
                permNum[t[0]][t[1]][t[2]][added] %= MOD;
                for (int[] nt : nextUp) {
                    permNum[nt[0]][nt[1]][nt[2]][added + 1] += permNum[t[0]][t[1]][t[2]][added];
                    permNum[nt[0]][nt[1]][nt[2]][added + 1] %= MOD;
                }
            }

            if (inPts == pointNum) {  // dw, this if statement should only run once
                validPerms = permNum[t[0]][t[1]][t[2]][inPts];
            }
        }
        System.out.println(validPerms);
        System.err.printf("today on lazy flavor text: this problem (%d ms)%n", System.currentTimeMillis() - start);
    }

    // sauce: https://stackoverflow.com/questions/2049582
    private static int signArea(Point p1, Point p2, Point p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    private static boolean inTriangle(Point pt, Point v1, Point v2, Point v3) {
        int d1 = signArea(pt, v1, v2);
        int d2 = signArea(pt, v2, v3);
        int d3 = signArea(pt, v3, v1);
        return !(((d1 < 0) || (d2 < 0) || (d3 < 0)) && ((d1 > 0) || (d2 > 0) || (d3 > 0)));
    }
}

class Point {
    int x;
    int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
