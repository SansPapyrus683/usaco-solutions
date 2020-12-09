package official.o2019.usopen.silver.stronkCows;

import java.io.*;
import java.util.*;

// 2019 usopen silver (waaay too slow for last test case lol)
public class CowJump {
    private static class Point {
        public int x, y;
        public Point(int x, int y){
            this.x = x; this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", x, y);
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader read = new BufferedReader(new FileReader("cowjump.in"));
        int segmentNum = Integer.parseInt(read.readLine());
        Point[][] segments = new Point[segmentNum][4];
        for (int s = 0; s < segmentNum; s++) {
            int[] rawSegment = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            segments[s] = new Point[] {new Point(rawSegment[0], rawSegment[1]), new Point(rawSegment[2], rawSegment[3])};
        }
        Point[][] originalSegments = segments.clone();
        Arrays.sort(segments, (a, b) -> a[0].x != b[0].x ? a[0].x - b[0].x : a[1].x - b[1].x);

        int[] badIndices = new int[] {-1, -1};
        // you may have outsmarted me by putting the intersection at the back, but i outsmarted your outsmarting!
        searching:
        for (int s1 = segmentNum - 1; s1 >= 0; s1--) {
            for (int s2 = s1 - 1; s2 >= 0; s2--) {
                // see if we've gone past the "no hope" point- where even the x intervals don't line up (we know bc sorted above)
                if (Math.max(segments[s2][0].x, segments[s2][1].x) < Math.min(segments[s1][0].x, segments[s1][1].x)) {
                    break;
                }
                if (intersecting(segments[s1], segments[s2])) {
                    badIndices = new int[] {s2, s1};
                    break searching;
                }
            }
        }

        int[] indicesIntersections = new int[2];  // ok so we found the intersection, let's see which to remove
        for (int s = 0; s < segmentNum; s++) {
            if (s == badIndices[0] || s == badIndices[1]) {
                continue;
            }
            if (intersecting(segments[badIndices[0]], segments[s])) {
                indicesIntersections[0]++;
            }
            if (intersecting(segments[badIndices[1]], segments[s])) {
                indicesIntersections[1]++;
            }
        }
        // default to the first one (the earlier one) but if the second one has more intersections remove that one
        Point[] toRemove = indicesIntersections[0] >= indicesIntersections[1] ? segments[badIndices[0]] : segments[badIndices[1]];
        int ans = 1;
        while (!Arrays.equals(originalSegments[ans - 1], toRemove)) {
            ans++;
        }
        PrintWriter written = new PrintWriter("cowjump.out");
        written.println(ans);
        written.close();
        System.out.println(ans);
        System.out.printf("MEMES. (%d ms lol)%n", System.currentTimeMillis() - start);
    }

    public static int sign(Point a, Point b, Point c) {
        return Integer.compare((b.x - a.x) * (c.y-a.y) - (c.x-a.x) * (b.y-a.y), 0);
    }

    public static boolean between(Point p, Point x, Point y) {  // checks if p is between x and y
        return ((x.x <= p.x && p.x <= y.x) || (y.x <= p.x && p.x <= x.x))
                && ((x.y <= p.y && p.y <= y.y) || (y.y <= p.y && p.y <= x.y));
    }

    public static boolean intersecting(Point[] s1, Point[] s2) {
        return intersecting(s1[0], s1[1], s2[0], s2[1]);
    }

    // touching at endpoints do count as intersecting (copied from some random book lmao)
    public static boolean intersecting(Point s1Start, Point s1End, Point s2Start, Point s2End) {
        int[] signs = {sign(s1Start, s2Start, s2End), sign(s1End, s2Start, s2End),
                sign(s2Start, s1Start, s1End), sign(s2End, s1Start, s1End)};

        if (Arrays.stream(signs).allMatch(Integer.valueOf(0)::equals)) {
            return between(s1Start, s2Start, s2End) || between(s1End, s2Start, s2End) || between(s2Start, s1Start, s1End);
        }
        return signs[0] != signs[1] && signs[2] != signs[3];
    }
}
