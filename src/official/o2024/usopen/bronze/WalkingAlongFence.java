package official.o2024.usopen.bronze;

import java.awt.Point;
import java.io.*;
import java.util.*;

/** 2024 us open bronze */
public class WalkingAlongFence {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer initial = new StringTokenizer(read.readLine());
        int cowNum = Integer.parseInt(initial.nextToken());
        int postNum = Integer.parseInt(initial.nextToken());
        Point[] posts = new Point[postNum];
        Map<Integer, List<Integer>> byX = new HashMap<>();
        Map<Integer, List<Integer>> byY = new HashMap<>();
        for (int p = 0; p < postNum; p++) {
            StringTokenizer pt = new StringTokenizer(read.readLine());
            posts[p] =
                    new Point(Integer.parseInt(pt.nextToken()), Integer.parseInt(pt.nextToken()));
            if (!byX.containsKey(posts[p].x)) {
                byX.put(posts[p].x, new ArrayList<>());
            }
            byX.get(posts[p].x).add(p);
            if (!byY.containsKey(posts[p].y)) {
                byY.put(posts[p].y, new ArrayList<>());
            }
            byY.get(posts[p].y).add(p);
        }

        List<Integer>[] neighbors = new ArrayList[postNum];
        for (int p = 0; p < postNum; p++) {
            neighbors[p] = new ArrayList<>();
        }
        for (List<Integer> ySet : byX.values()) {
            ySet.sort(Comparator.comparingInt(p -> posts[p].y));
            for (int i = 0; i < ySet.size(); i += 2) {
                neighbors[ySet.get(i)].add(ySet.get(i + 1));
                neighbors[ySet.get(i + 1)].add(ySet.get(i));
            }
        }
        for (List<Integer> xSet : byY.values()) {
            xSet.sort(Comparator.comparingInt(p -> posts[p].x));
            for (int i = 0; i < xSet.size(); i += 2) {
                neighbors[xSet.get(i)].add(xSet.get(i + 1));
                neighbors[xSet.get(i + 1)].add(xSet.get(i));
            }
        }

        Map<Point, Integer> ptInds = new HashMap<>();
        int last = -1;
        int at = 0;
        for (int p = 0; p < postNum; p++) {
            int moveTo = neighbors[at].get(0);
            if (p > 0 && moveTo == last) {
                moveTo = neighbors[at].get(1);
            }

            Point delta =
                    new Point(
                            (int) Math.signum(posts[moveTo].x - posts[at].x),
                            (int) Math.signum(posts[moveTo].y - posts[at].y));
            Point curr = posts[at];
            while (!curr.equals(posts[moveTo])) {
                ptInds.put(curr, ptInds.size());
                curr = new Point(curr.x + delta.x, curr.y + delta.y);
            }

            last = at;
            at = moveTo;
        }

        for (int c = 0; c < cowNum; c++) {
            StringTokenizer cow = new StringTokenizer(read.readLine());
            int sx = Integer.parseInt(cow.nextToken());
            int sy = Integer.parseInt(cow.nextToken());
            int ex = Integer.parseInt(cow.nextToken());
            int ey = Integer.parseInt(cow.nextToken());
            int sInd = ptInds.get(new Point(sx, sy));
            int eInd = ptInds.get(new Point(ex, ey));
            int dist = Math.abs(eInd - sInd);
            System.out.println(Math.min(dist, ptInds.size() - dist));
        }
    }
}
